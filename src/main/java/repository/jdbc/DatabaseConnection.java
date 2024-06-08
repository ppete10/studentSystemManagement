package repository.jdbc;

import java.sql.*;

public class DatabaseConnection {

    //Access jdbc:odbc:dataSource
    //MySQL jdbc:mysql://hostname:port/dbname
    //Oracle jdbc:oracle:thin//hostname:port:oracleDBSID
    private static final String DBMS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "030130";

    private static final String DATABASENAME = "SystemStudentRepo";
    private static final String JDBC_URL = URL + DATABASENAME;

    //Access: sun.jdbc.odbc.JdbcOdbcDriver
    //MySQL: com.mysql.cj.jdbc.Driver
    //Oracle: oracle.jdbc.driver.OracleDriver
    static {
        try {
            Class.forName(DBMS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!isDatabaseExists(DATABASENAME)) {
            createDatabaseIfNotExists();
        }
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private static boolean isDatabaseExists(String databaseName) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(databaseName)) {
                    return true;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet resultSet = conn.getMetaData().getCatalogs();
            boolean databaseExists = false;
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equals("SystemStudentRepo")) {
                    databaseExists = true;
                    break;
                }
            }
            resultSet.close();

            if (!databaseExists) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS SystemStudentRepo");
                System.out.println("Database 'SystemStudentRepo' created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
