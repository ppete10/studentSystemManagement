package repository.jdbc;

import java.sql.*;
public class DatabaseConnection {

    //Access: sun.jdbc.odbc.JdbcOdbcDriver
    //MySQL: com.mysql.cj.jdbc.Driver
    //Oracle: oracle.jdbc.driver.OracleDriver
    private static DBMS selectedDBMS = DBMS.MYSQL; //Default
    //Access jdbc:odbc:dataSource
    //MySQL jdbc:mysql://hostname:port/dbname
    //Oracle jdbc:oracle:thin//hostname:port:oracleDBSID
    private static String USERNAME;
    private static String PASSWORD;
    private static final String DATABASENAME = "SystemStudentRepo";

    public DatabaseConnection(String USERNAME, String PASSWORD) {
        DatabaseConnection.USERNAME = USERNAME;
        DatabaseConnection.PASSWORD = PASSWORD;
    }

    public void setDBMS(DBMS dbms) {
        selectedDBMS = dbms;
        try {
            Class.forName(selectedDBMS.getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver",e);
        }
    }
    private static String getJdbcUrl(){
        return selectedDBMS.getUrl() + DATABASENAME;
    }

    public static Connection getConnection() throws SQLException {
        if (!isDatabaseExists()) {
            createDatabaseIfNotExists();
        }
        return DriverManager.getConnection(getJdbcUrl(), USERNAME, PASSWORD);
    }

    private static boolean isDatabaseExists() {
        try (Connection conn = DriverManager.getConnection(selectedDBMS.getUrl(), USERNAME, PASSWORD)) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(DATABASENAME)) {
                    return true;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return false;
    }


    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(selectedDBMS.getUrl(), USERNAME, PASSWORD);
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
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+ DATABASENAME);
                System.out.println("Database 'SystemStudentRepo' created successfully.");
            }
        } catch (SQLException ignored) {
            throw new RuntimeException();
        }
    }
}
