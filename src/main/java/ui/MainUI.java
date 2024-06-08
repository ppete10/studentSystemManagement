package ui;

import entities.Student;
import repository.file.FileCourseRepo;
import repository.file.FileEnrollmentRepo;
import repository.file.FileStudentRepo;
import repository.jdbc.JdbcCourseRepo;
import repository.jdbc.JdbcEnrollmentRepo;
import repository.jdbc.JdbcStudentRepo;
import repository.memory.MemCourseRepo;
import repository.memory.MemEnrollmentRepo;
import repository.memory.MemStudentRepo;
import service.SystemService;

import java.io.Console;
import java.util.Scanner;

import static ui.CourseUI.*;
import static ui.EnrollmentUI.*;
import static ui.StudentUI.*;

public class MainUI {
    static SystemService systemServices;
    static final Scanner scanner = new Scanner(System.in);
    private static boolean continueRunning = true;


    public static void start() {
        System.out.println("         Welcome to Student System Management");
        System.out.println("=======================================================");
        System.out.println("Please Login.");
        String username = "admin";
        String password = "int103";
        String typePass;
        String typeUser;

        Console cons = System.console();
        while (true) {
            if (cons != null) {
                System.out.print("Username: ");
                typeUser = cons.readLine();
                System.out.print("Password: ");
                typePass = new String(cons.readPassword());
            } else {
                System.out.println("[Public]");
                System.out.print("Username: ");
                typeUser = scanner.nextLine();
                System.out.print("Password: ");
                typePass = scanner.nextLine();
            }

            if (typeUser.equals(username) && typePass.equals(password)) {
                run();

                System.out.println("=======================================================");
                System.out.println("Thank you!! for using Student System Management");
                System.out.println("Have a nice day!!");
                break;
            } else {
                System.out.println("Invalid Username or Password, Try agian!!");
            }
        }
    }

    private static void run() {
        while (continueRunning) {
            storage();
            selectRole();
        }
    }

    //Select storageType
    private static void storage() {
        System.out.println("=======================================================");
        System.out.println("Please select storage type[File, jdbc, Memory]");

        while (true) {
            System.out.print("Storage type: ");
            String typeStorage = scanner.nextLine();
            switch (typeStorage.toLowerCase()) {
                case "file":
                    try {
                        systemServices = new SystemService(new FileStudentRepo(), new FileEnrollmentRepo(), new FileCourseRepo());
                        System.out.println("Using file storage type.");
                        break;
                    } catch (Exception e) {
                        System.out.println("File storage type cannot be used.");
                        continue;
                    }
                case "jdbc":
                    try {
                        systemServices = new SystemService(new JdbcStudentRepo(), new JdbcEnrollmentRepo(), new JdbcCourseRepo());
                        System.out.println("Database 'SystemStudentRepo' Connected");
                        break;
                    } catch (Exception e) {
                        System.out.println("Database storage type cannot be used.");
                        continue;
                    }
                case "memory":
                    systemServices = new SystemService(new MemStudentRepo(), new MemEnrollmentRepo(), new MemCourseRepo());
                    System.out.println("Using memory storage type.");
                    break;
                default:
                    System.out.println("Invalid option, Please select [File, jdbc, Memory]!");
                    continue;
            }
            break;
        }
    }

    private static void selectRole() {
        String menu = """
                ====================== Login ======================
                1. Login for Student.
                2. Login for Teacher.
                3. Select Storage Type.""";
        label:
        while (true) {
            System.out.println(menu);
            System.out.print("Select an option (Enter '0' to quit): ");
            String role = scanner.nextLine();
            switch (role) {
                case "0":
                    continueRunning = false;
                    break label;
                case "1":
                    studentLogin();
                    break;
                case "2":
                    teacherActions();
                    break;
                case "3":
                    break label;
                default:
                    System.out.println("Invalid option. Please select [0-3].");
                    break;
            }
        }
    }

    private static void studentLogin() {
        System.out.println("====================== Student login ======================");
        System.out.println("Login by Student ID (Enter '0' to back)");
        Student student;

        while (true) {
            System.out.print("Student ID: ");
            String studentId = scanner.nextLine().toUpperCase();
            if (studentId.equals("0")) {
                break;
            }

            student = systemServices.findStudentById(studentId);
            if (student != null) {
                System.out.println("Login success!!");
                studentAction(student.getStudentId());
                break;
            } else {
                System.out.println("Invalid Student ID, Please try agian. (Enter '0' to back)");
            }

        }
    }

    private static void studentAction(String studentId) {

        while (true) {
            String menu = """
                    \n===== Menu For Student =====
                    1. Show your Info
                    2. Update Info
                    3. View All Courses
                    4. Enroll in Courses
                    5. View your Enrollments
                    ------------------------------
                    6. Logout
                    """;
            System.out.println(menu);
            System.out.print("Select Menu[1-6]: ");
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
                    showYourInfo(studentId);
                    break;
                case 2:
                    updateStudentById(studentId);
                    break;
                case 3:
                    viewAllCourses();
                    break;
                case 4:
                    enrollStudentInCourse(studentId);
                    break;
                case 5:
                    findEnrollmentByStudentId(studentId);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


    private static void teacherActions() {
        while (true) {
            String menu = """
                    \n======= Menu For Teacher =======
                    Student Manage:
                    1. Register Student
                    2. Update Student
                    3. Delete Student
                    4. Find Student by ID
                    5. View All Students
                    ------------------------------
                    Course Manage:
                    6. Add Course
                    7. Update Course
                    8. Delete Course
                    9. Find Course by Code
                    10.View All Courses
                    ------------------------------
                    Enroll Manage:
                    11. Enroll Student in Course
                    12. Update Enrollment
                    13. Delete Enrollment
                    14. Find Enrollment by ID
                    15. View All Enrollments
                    ------------------------------
                    16. Logout
                    """;
            System.out.println(menu);
            System.out.print("Select Menu[1-16]: ");
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
                    registerStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    findStudentById();
                    break;
                case 5:
                    viewAllStudents();
                    break;
                case 6:
                    addCourse();
                    break;
                case 7:
                    updateCourse();
                    break;
                case 8:
                    deleteCourse();
                    break;
                case 9:
                    viewCourseByCode();
                    break;
                case 10:
                    viewAllCourses();
                    break;
                case 11:
                    enrollStudentInCourse();
                    break;
                case 12:
                    changeEnrollment();
                    break;
                case 13:
                    deleteEnrollment();
                    break;
                case 14:
                    viewEnrollment();
                    break;
                case 15:
                    viewAllEnrollments();
                    break;
                case 16:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
