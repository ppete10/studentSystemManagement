package repository.jdbc;

import java.sql.*;

public class DatabaseConnection {

    private static DBMS selectedDBMS = DBMS.MYSQL; //Default
    private static String USERNAME;
    private static String PASSWORD;
    private static final String DATABASENAME = "studentsystemrepo";

    public DatabaseConnection(String USERNAME, String PASSWORD) {
        DatabaseConnection.USERNAME = USERNAME;
        DatabaseConnection.PASSWORD = PASSWORD;
    }

    public void setDBMS(DBMS dbms) {
        selectedDBMS = dbms;
        try {
            Class.forName(selectedDBMS.getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    private static String getJdbcUrl() {
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
            throw new RuntimeException("Cannot connect to the database.");
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
                if (databaseName.equals(DATABASENAME)) {
                    databaseExists = true;
                    break;
                }
            }
            resultSet.close();

            if (!databaseExists) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASENAME);
                System.out.println("Database 'studentsystemrepo' created successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create the Database");
        }
    }
}
