package ui;


import repository.jdbc.DBMS;
import repository.jdbc.DatabaseConnection;

import java.io.Console;
import java.util.Scanner;

public class JdbcUI {
    static Scanner scanner = new Scanner(System.in);
    static DatabaseConnection DBConnect;
    static String pass;
    static String username;

    public static void connectDB() {
        login();
        menujdbc();
    }

    private static void login() {
        System.out.println("======= Connect to Database Server =======");
        System.out.println("Please login *");
        Console cons = System.console();
        if (cons != null) {
            System.out.print("User: ");
            username = cons.readLine();
            System.out.print("Password: ");
            pass = new String(cons.readPassword());
        } else {
            System.out.println("[Public]");
            System.out.print("User: ");
            username = scanner.nextLine();
            System.out.print("Password: ");
            pass = scanner.nextLine();
        }
        DBConnect = new DatabaseConnection(username, pass);
    }

    private static void menujdbc() {
        boolean exit = false;
        String menu = """
                =========== Database Configuration ===========
                1. Change USER PASSWORD.
                2. Select DBMS.
                3. Connect to Database.
                4. Exit.""";
        while (!exit) {
            System.out.println(menu);
            System.out.print("Select an option: ");
            int option;
            try {
                String input = scanner.nextLine().trim();
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Menu. Try again.");
                continue;
            }
            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    changeDBMS();
                    break;
                case 3:
                    connectToDB();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void connectToDB() {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Database 'systemstudentrepo' Connected");
        } catch (Exception e) {
            System.out.println("Failed to connect");
            System.out.println("Please check your Driver, URL DBMS and USER, PASSWORD. Try again!.");
        }
    }

    private static void changeDBMS() {
        while (true) {
            try {
                System.out.println("=== Select DBMS ===");
                DBMS[] dbmsValues = DBMS.values();
                for (int i = 0; i < dbmsValues.length; i++) {
                    System.out.println((i + 1) + ". " + dbmsValues[i].name());
                }
                System.out.print("Select an option: ");
                String input = scanner.nextLine();
                int dbmsChoice = Integer.parseInt(input);

                if (dbmsChoice == 2) {
                    System.out.println("""
                            ############# DBMS  Driver #############
                            ---------------- Example ---------------
                            MySQL: com.mysql.cj.jdbc.Driver
                            Oracle: oracle.jdbc.driver.OracleDriver
                            Access: sun.jdbc.odbc.JdbcOdbcDriver
                            ----------------------------------------""");
                    System.out.print("Enter driver: ");
                    String customDriver = scanner.nextLine();

                    System.out.println("""
                            ############### DBMS URL ###############
                            ---------------- Example ---------------
                            MySQL: jdbc:mysql://localhost:3306/
                            Oracle: jdbc:oracle:thin//hostname:port:
                            Access: jdbc:odbc:dataSource
                            ----------------------------------------""");
                    System.out.print("Enter URL: ");
                    String customUrl = scanner.nextLine();

                    DBMS customDBMS = DBMS.CUSTOM;
                    customDBMS.setDriverClass(customDriver);
                    customDBMS.setUrl(customUrl);
                    DBConnect.setDBMS(customDBMS);
                    System.out.println("Custom DBMS set successfully.");
                    break;
                } else {
                    DBMS selectedDBMS = DBMS.values()[dbmsChoice - 1];
                    DBConnect.setDBMS(selectedDBMS);
                    System.out.println("DBMS set successfully.");
                    break;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid choice. Try again.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

}
