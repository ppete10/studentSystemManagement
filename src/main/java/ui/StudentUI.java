package ui;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import exception.InvalidCourseFormatException;
import exception.InvalidEnrollmentFormatException;
import exception.InvalidStudentFormatException;
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
import java.util.*;
import java.util.stream.Stream;

public class StudentUI {
    private static SystemService systemServices;
    private static final Scanner scanner = new Scanner(System.in);
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
            String typeStorage = scanner.nextLine();
            switch (typeStorage.toLowerCase()) {
                case "file":
                    systemServices = new SystemService(new FileStudentRepo(), new FileEnrollmentRepo(), new FileCourseRepo());
                    break;
                case "jdbc":
                    systemServices = new SystemService(new JdbcStudentRepo(), new JdbcEnrollmentRepo(), new JdbcCourseRepo());
                    break;
                case "memory":
                    systemServices = new SystemService(new MemStudentRepo(), new MemEnrollmentRepo(), new MemCourseRepo());
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
                3. Select Storage Type.
                (Enter '0' to quit).""";
        label:
        while (true) {
            System.out.println(menu);
            System.out.print("Select an option: ");
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


    // Manage Student UI
    private static void registerStudent() {
        String name = null;
        int age = 0;
        int year = 0;
        boolean isValid = false;
        System.out.println("======= Register Student =======");
        while (!isValid) {
            try {
                System.out.print("Enter Name: ");
                name = scanner.nextLine();
                if (name.trim().isEmpty()) {
                    throw new InvalidStudentFormatException("Name cannot be empty");
                }
                isValid = true;
            } catch (InvalidStudentFormatException e) {
                System.out.println("Invalid name. Try again!.");
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter age: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Age cannot be empty.");
                }
                age = Integer.parseInt(input);
                if (age > 0 && age <= 100) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer and less than 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter year: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Year cannot be empty.");
                }
                year = Integer.parseInt(input);
                if (year > 0 && year <= 4) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Year must be a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Student student = systemServices.registerStudent(name, age, year);
        if (student != null) {
            System.out.println(" Student registered successfully:");
            System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
            System.out.println("-------------------------------------");
            System.out.println(student);
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Failed to register student.");
        }
    }

    private static void updateStudent() {
        viewAllStudents();
        System.out.println("====== Update Student ======");
        Student student = checkStudentId();
        if (student != null) {
            String studentId = student.getStudentId();
            System.out.println(studentId);
            updateStudentById(studentId);
        } else {
            System.out.println("Updates cancelled.");
        }

    }

    private static void updateStudentById(String studentId) {
        String name = null;
        int age = 0;
        int year = 0;
        boolean isValid = false;
        Student studentOld = systemServices.findStudentById(studentId);
        System.out.println("          Old Student Info");
        System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
        System.out.println("-------------------------------------");
        System.out.println(studentOld);
        System.out.println("-------------------------------------");
        while (!isValid) {
            try {
                System.out.print("Enter new Name: ");
                name = scanner.nextLine();
                if (name.trim().isEmpty()) {
                    throw new InvalidStudentFormatException("Name cannot be empty");
                }
                isValid = true;
            } catch (InvalidStudentFormatException e) {
                System.out.println("Invalid name. Try again!.");
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter new age: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Age cannot be empty.");
                }
                age = Integer.parseInt(input);
                if (age > 0 && age <= 100) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer and less than 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter new Year: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Year cannot be empty.");
                }
                year = Integer.parseInt(input);
                if (year > 0 && year <= 4) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Year must be a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Student student = systemServices.reStudent(studentId, name, age, year);
        System.out.println("          Old Student Info");
        System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
        System.out.println("-------------------------------------");
        System.out.println(student);
        System.out.println("-------------------------------------");
    }

    private static Student checkStudentId() {
        String studentId;

        while (true) {
            try {
                System.out.print("Enter student ID or Exit[0]: ");
                studentId = scanner.nextLine().toUpperCase();
                if (studentId.isEmpty() || studentId.isBlank()) {
                    throw new InvalidStudentFormatException("Student ID cannot be empty");
                }
                if (studentId.equals("0")) {
                    System.out.println("Cancelling...");
                    return null;
                }
                Student student = systemServices.findStudentById(studentId);
                if (student != null) {
                    return student;
                } else {
                    System.out.println("Not Found student ID. Try again!.");
                }
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteStudent() {
        viewAllStudents();
        System.out.println("====== Delete Student ======");
        Student studentCheck = checkStudentId();
        if (studentCheck != null) {
            String studentId = studentCheck.getStudentId();
            Student student = systemServices.deleteStudent(studentId);
            if (student != null) {
                System.out.println("Student deleted successfully.");
            }
        } else {
            System.out.println("Failed to delete student.");
        }

    }

    private static void findStudentById() {
        System.out.println("====== Find Student By ID======");
        Student studentcheck = checkStudentId();
        if (studentcheck != null) {
            String studentId = studentcheck.getStudentId();
            Student student = systemServices.findStudentById(studentId);
            if (student != null) {
                System.out.println("            Found students");
                System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
                System.out.println("-------------------------------------");
                System.out.println(student);
                System.out.println("-------------------------------------");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void viewAllStudents() {
        Stream<Student> studentStream = systemServices.getAllStudent();
        if (studentStream.findAny().isPresent()) {
            studentStream = systemServices.getAllStudent();
            System.out.println("              All students");
            System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
            System.out.println("-------------------------------------");
            studentStream.forEach(System.out::println);
            System.out.println("-------------------------------------");
        } else {
            System.out.println("No students available.");
        }
    }

    private static void showYourInfo(String studentId) {
        Student student = systemServices.findStudentById(studentId);
        System.out.println("              Your Info");
        System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
        System.out.println("-------------------------------------");
        System.out.println(student);
        System.out.println("-------------------------------------");
    }

    // Manage Enroll UI
    private static void enrollStudentInCourse() {
        viewAllStudents();
        if (systemServices.getAllStudent().findAny().isPresent()) {
            System.out.println("====== Enroll Student In Course ======");
            Student checkStudent = checkStudentId();

            if (checkStudent != null) {
                String studentId = checkStudent.getStudentId();
                enrollStudentInCourse(studentId);
            }

        }else {
            System.out.println("Enroll Student has Cancelled.");
        }
    }

    private static void enrollStudentInCourse(String studentId) {
        Enrollment checkEnroll = systemServices.findEnrollmentByStudentId(studentId);

        if (checkEnroll != null) {
            System.out.println("                 Has enrolled already!");
            System.out.printf("%-6s %-7s %-30s %-3s%n","ID", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(checkEnroll);
            System.out.println("---------------------------------------------------------");
            return;
        }

        List<Course> availableCourses = new ArrayList<>();
        systemServices.getAllCourses().forEach(availableCourses::add);

        List<Course> selectedCourses = new ArrayList<>();
        if (availableCourses.isEmpty()) {
            System.out.println("No Courses to Enroll");
            return;
        }

        while (true) {

            System.out.println("                   Available Courses:");
            System.out.printf("%-6s %-7s %-30s %-3s%n","Number", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ".     " + availableCourses.get(i).toStringFormat());
            }
            if (availableCourses.isEmpty()){
                System.out.println("             No Course Available.");
            }
            System.out.println("---------------------------------------------------------");
            System.out.print("Select Number (0 to exit):");

            String input = scanner.nextLine();

            try {
                int option = Integer.parseInt(input);
                if (option == 0) {
                    break;
                } else if (option > 0 && option <= availableCourses.size()) {
                    Course selectedCourse = availableCourses.get(option - 1);

                    if (!selectedCourses.contains(selectedCourse)) {
                        selectedCourses.add(selectedCourse);
                        availableCourses.remove(selectedCourse);
                        System.out.println("Course " + selectedCourse.getCourseCode() + " added.");
                    } else {
                        System.out.println("Have already selected this course.");
                    }
                } else {
                    System.out.println("Invalid Number. Please try again.");
                }
            } catch ( NumberFormatException e ){
                System.out.println("Invalid Number. Please try again.");
            }
        }
        if (!selectedCourses.isEmpty()) {
            Enrollment enrollment = systemServices.enrollStudentInCourse(studentId, selectedCourses.toArray(new Course[0]));
            System.out.println("                   Enroll In Courses Success.");
            System.out.printf("%-6s %-7s %-30s %-3s%n","ID", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(enrollment);
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("No courses Enrolled.");
        }
    }

    private static void changeEnrollment() {
        viewAllEnrollments();
        try {
            Stream<Enrollment> any = systemServices.getAllEnrollment();
            if(any.findAny().isEmpty()){
                System.out.println("Update Enroll has Cancelled.");
                return;
            }
        } catch (NullPointerException e){
            System.out.println("Update Enroll has Cancelled.");
            return;
        }

        System.out.println("====== Update Enrollment ======");
        Enrollment checkEnrollId = checkEnrollId();
        if (checkEnrollId == null){
            System.out.println("Update Enroll has Cancelled.");
            return;
        }
        String enrollId = checkEnrollId.getStudentEnrollId();

        Enrollment studentEnroll = systemServices.findEnrollmentByStudentId(enrollId);
        System.out.println(studentEnroll.getStudentEnrollId() + " has Enroll " + studentEnroll.getCourse());

        List<Course> availableCourses = new ArrayList<>();
        List<Course> selectedCourses = new ArrayList<>();
        systemServices.getAllCourses().forEach(availableCourses::add);



        if (availableCourses.isEmpty()) {
            System.out.println("No Courses to Enroll");
            return;
        }

        while (true) {
            System.out.println("\n                   List All Courses");
            System.out.printf("%-6s %-7s %-30s %-3s%n","Number", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ".     " + availableCourses.get(i).toStringFormat());
            }
            if (availableCourses.isEmpty()){
                System.out.println("                 No Course Available.");
            }
            System.out.println("---------------------------------------------------------");
            System.out.println("This menu will replace all courses. If no courses are selected, it will be empty.");
            System.out.print("Select Number (0 to exit):");

            try {
                String input = scanner.nextLine();
                int option = Integer.parseInt(input);

                if (option == 0) {
                    break;
                } else if (option > 0 && option <= availableCourses.size()) {
                    Course selectedCourse = availableCourses.get(option - 1);
                    if (!selectedCourses.contains(selectedCourse)) {
                        selectedCourses.add(selectedCourse);
                        availableCourses.remove(selectedCourse);
                        System.out.println("Course " + selectedCourse.getCourseCode() + " added.");
                    } else {
                        System.out.println("You have already selected this course.");
                    }
                } else {
                    System.out.println("Invalid Number. Please try again.");
                }
            } catch (InvalidStudentFormatException | NumberFormatException e){
                System.out.println("Invalid Number. Please try again.");
            }
        }
        if (!selectedCourses.isEmpty()) {
            Set<Course> setCourse = new HashSet<>(selectedCourses);
            Enrollment enrollment = systemServices.changeEnrollment(enrollId,  setCourse);
            System.out.println("                 Update Enroll Success.");
            System.out.printf("%-6s %-7s %-30s %-3s%n","Number", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(enrollment);
            System.out.println("---------------------------------------------------------");
        } else {
            systemServices.changeEnrollment(enrollId, new HashSet<>());
            System.out.println("No courses Enrolled.");
        }
    }

    private static void deleteEnrollment() {
        viewAllEnrollments();
        try {
            Stream<Enrollment> any = systemServices.getAllEnrollment();
            if(any.findAny().isEmpty()){
                System.out.println("Delete Enrollment has Cancelled.");
                return;
            }
        } catch (NullPointerException e){
            System.out.println("Delete Enrollment has Cancelled.");
            return;
        }

        System.out.println("====== Delete Enrollment ======");
        Enrollment checkEnrollId = checkEnrollId();
        if (checkEnrollId == null){
            System.out.println("Delete Enrollment has Cancelled.");
            return;
        }
        String EnrollId = checkEnrollId.getStudentEnrollId();

        Enrollment enrollment = systemServices.deleteEnrollment(EnrollId);
        if (enrollment != null) {
            System.out.println("Enrollment deleted successfully.");
        } else {
            System.out.println("Failed to delete enrollment.");
        }
    }

    private static Enrollment checkEnrollId(){
        String enrollId;

        while (true) {
            try {
                System.out.print("Enter Enroll ID ID or Exit[0]: ");
                enrollId = scanner.nextLine().toUpperCase();
                if (enrollId.isEmpty() || enrollId.isBlank()) {
                    throw new InvalidEnrollmentFormatException("Student ID cannot be empty");
                }
                if (enrollId.equals("0")) {
                    System.out.println("Cancelling...");
                    return null;
                }
                Enrollment enrollment = systemServices.findEnrollmentByStudentId(enrollId);
                if (enrollment != null) {
                    return enrollment;
                } else {
                    System.out.println("Not Found student ID. Try again!.");
                }
            } catch (InvalidEnrollmentFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void viewEnrollment() {
        Student checkStudent = checkStudentId();
        if (checkStudent == null){
            System.out.println("Find Enrollment has Cancelled.");
            return;
        }
        String studentId = checkStudent.getStudentId();
        findEnrollmentByStudentId(studentId);
    }

    private static void findEnrollmentByStudentId(String studentId) {
        Enrollment enrollment = systemServices.findEnrollmentByStudentId(studentId);
        System.out.println("                 Find Enrollment By ID");
        if (enrollment != null) {
            System.out.printf("%-6s %-7s %-30s %-3s%n","ID", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(enrollment);
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    private static void viewAllEnrollments() {
        try {
            Stream<Enrollment> enrollments = systemServices.getAllEnrollment();
            if (enrollments.findAny().isPresent()) {
                enrollments = systemServices.getAllEnrollment();
                System.out.println("                   All Enrollments ");
                System.out.printf("%-6s %-7s %-30s %-3s%n","ID", "Code", "Name", "Credits");
                System.out.println("---------------------------------------------------------");
                enrollments.forEach(System.out::println);
                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("No enrollments available.");
            }
        } catch (NullPointerException e){
            System.out.println("No enrollments available.");
        }

    }

    //manage Course UI
    private static void addCourse() {
        String courseCode = null;
        String courseName = null;
        int credits = 0;
        boolean isValid = false;
        System.out.println("====== Add Course ======");
        while (!isValid) {
            try {
                System.out.print("Enter course code: ");
                courseCode = scanner.nextLine().toUpperCase();
                if (courseCode.trim().isEmpty()) {
                    throw new InvalidCourseFormatException("Course code cannot be empty");
                }

                Course c = systemServices.getCourseByCode(courseCode);
                if (c != null) {
                    System.out.println("Has course code already!");
                    System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
                    System.out.println("---------------------------------------------------------");
                    System.out.println(c.toStringFormat());
                    System.out.println("---------------------------------------------------------");
                    return;
                } else {
                    isValid = true;
                }
            } catch (InvalidCourseFormatException e) {
                System.out.println("Invalid course code. Try again!.");
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter course name: ");
                courseName = scanner.nextLine();
                if (courseName.trim().isEmpty()) {
                    throw new InvalidCourseFormatException("Course name cannot be empty");
                }
                isValid = true;
            } catch (InvalidCourseFormatException e) {
                System.out.println("Invalid course name. Try again!.");
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter credits: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Credits cannot be empty.");
                }
                credits = Integer.parseInt(input);
                if (credits > 0) {
                    Course course = systemServices.addCourse(courseCode, courseName, credits);
                    if (course != null) {
                        System.out.println("              Course added successfully:");
                        System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
                        System.out.println("---------------------------------------------------------");
                        System.out.println(course.toStringFormat());
                        System.out.println("---------------------------------------------------------");
                    } else {
                        System.out.println("Failed to add course.");
                    }
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Credits must be a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid credits. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateCourse() {
        String courseCode = null;
        String courseName = null;
        int credits = 0;
        boolean isValid = false;

        viewAllCourses();
        System.out.println("====== Update Course ======");
        while (!isValid) {
            try {
                System.out.print("Enter course code: ");
                courseCode = scanner.nextLine().toUpperCase();
                if (courseCode.trim().isEmpty()) {
                    throw new InvalidCourseFormatException("Course code cannot be empty");
                }
                isValid = true;

            } catch (InvalidCourseFormatException e) {
                System.out.println("Invalid course code. Try again!.");
            }
        }
        Course c = systemServices.getCourseByCode(courseCode);
        if (c == null) {
            System.out.println("Course not found.");
            isValid = false;
            return;
        }

        System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
        System.out.println(c);

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter new course name: ");
                courseName = scanner.nextLine();
                if (courseName.trim().isEmpty()) {
                    throw new InvalidCourseFormatException("Course name cannot be empty");
                }
                isValid = true;
            } catch (InvalidCourseFormatException e) {
                System.out.println("Invalid course name. Try again!.");
            }
        }

        isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter new credits: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.isBlank()) {
                    throw new InvalidStudentFormatException("Credits cannot be empty.");
                }
                credits = Integer.parseInt(input);
                if (credits > 0) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Credits must be a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid credits. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Course course = systemServices.updateCourse(courseCode, courseName, credits);
        if (course != null) {
            System.out.println("               Course updated successfully:");
            System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(course.toStringFormat());
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("Failed to update course.");
        }
    }

    private static void deleteCourse() {
        String courseCode = null;

        viewAllCourses();
        System.out.println("====== Delete Course ======");
        try {
            System.out.print("Enter course code: ");
            courseCode = scanner.nextLine().toUpperCase();
            if (courseCode.trim().isEmpty()) {
                throw new InvalidCourseFormatException("Course code cannot be empty");
            }

        } catch (InvalidCourseFormatException e) {
            System.out.println("Invalid course code. Try again!.");
        }

        Course course = systemServices.deleteCourse(courseCode);
        if (course != null) {
            System.out.println(course.getCourseCode() + " has deleted successfully.");
        } else {
            System.out.println("Failed to delete course.");
        }
    }

    private static void viewCourseByCode() {
        String courseCode;

        System.out.println("====== View Course By Code ======");
        while (true) {
            try {
                System.out.print("Enter student ID or Exit[0]: ");
                courseCode = scanner.nextLine().toUpperCase();
                if (courseCode.isEmpty() || courseCode.isBlank()) {
                    throw new InvalidStudentFormatException("Student ID cannot be empty");
                }
                if (courseCode.equals("0")) {
                    System.out.println("Cancelling...");
                    return;
                }
                Course course = systemServices.getCourseByCode(courseCode);
                if (course != null) {
                    System.out.println("                      Course found");
                    System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
                    System.out.println("---------------------------------------------------------");
                    System.out.println(course.toStringFormat());
                    System.out.println("---------------------------------------------------------");
                } else {
                    System.out.println("Course not found.");
                }
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void viewAllCourses() {
        Stream<Course> courses = systemServices.getAllCourses();
        long count = courses.count();
        if (count > 0) {
            courses = systemServices.getAllCourses();
            System.out.println("                      All Courses");
            System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            courses.forEach(course -> System.out.println(course.toStringFormat()));
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("No courses available.");
        }
    }
}
