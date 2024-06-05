package ui;

import entities.Course;
import entities.Enrollment;
import entities.Student;
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
import service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class StudentUI {
    private static StudentService studentService;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean continueRunning = true;


    public static void start() {
        System.out.println("     Welcome to Student System Management");
        System.out.println("===============================================");
        System.out.println("Please Login.");
        String username = "admin";
        String password = "int103";
        String typePass;
        String typeUser;
        var cons = System.console();
        var sc = new Scanner(System.in);

        while (true) {
            if (cons != null) {
                System.out.print("Username: ");
                typeUser = sc.nextLine();
                System.out.print("Password: ");
                typePass = new String(cons.readPassword());
            } else {
                System.out.println("[Public]");
                System.out.print("Username: ");
                typeUser = sc.nextLine();
                System.out.println("Password: ");
                typePass = sc.nextLine();
            }

            if (typeUser.equals(username) && typePass.equals(password)) {
                run();

                System.out.println("===============================================");
                System.out.println("Thank you!! for using Student System Management");
                System.out.println("Have a nice day!!");
                break;
            } else {
                System.out.println("Invalid Username or Password, Try agian!!");
            }
        }
    }

    //Select storageType
    private static void storage() {
        System.out.println("Please select storage type[File, jdbc, Memory]");

        while (true) {
            String typeStorage = scanner.nextLine();
            switch (typeStorage.toLowerCase()) {
                case "file":
                    studentService = new StudentService(new FileStudentRepo(), new FileEnrollmentRepo(), new FileCourseRepo());
                    break;
                case "jdbc":
                    studentService = new StudentService(new JdbcStudentRepo(), new JdbcEnrollmentRepo(), new JdbcCourseRepo());
                    break;
                case "memory":
                    studentService = new StudentService(new MemStudentRepo(), new MemEnrollmentRepo(), new MemCourseRepo());
                    break;
                default:
                    System.out.println("Invalid option, Please select [File, jdbc, Memory]!");
                    continue;
            }
            break;
        }
    }

    private static void run() {
        while (continueRunning) {
            storage();
            selectRole();
        }
    }

    private static void selectRole() {
        while (true) {
            System.out.println("Select Storage Type[3]");
            System.out.println("Login for Student[1] or Teacher[2]? (Enter '0' to quit)");
            try {
                Integer role = scanner.nextInt();
                scanner.nextLine();
                if (role == 0) {
                    continueRunning = false;
                    break;
                } else if (role == 1) {
                    studentLogin();
                } else if (role == 2) {
                    teacherActions();
                } else if (role == 3) {
                    break;
                } else {
                    System.out.println("Invalid option. Please select Student[1] or Teacher[2].");
                }
            } catch (Exception e) {
                System.out.println("Invalid option. Please select Student[1] or Teacher[2].");
                scanner.nextLine();
            }
        }
    }

    private static void studentLogin() {
        System.out.print("=== Student login ===\n");
        System.out.println("Login by Student ID (Enter '0' to back)");
        Student student;

        while (true) {
            String studentId = scanner.nextLine();
            if (studentId.equals("0")) {
                break;
            }

            student = studentService.findStudentById(studentId);
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
                    6. Logout
                    """;
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();

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


    private static void showYourInfo(String studentId) {
        Student student = studentService.findStudentById(studentId);
        System.out.println("Your Student ID: " + student.getStudentId());
        System.out.println(student.getName());
        System.out.println("Age: " + student.getAge() + " Year: " + student.getYear());
    }



    private static void teacherActions() {
        while (true) {
            String menu = """
                    \n===== Menu For Teacher =====
                    Select menu [1-16].
                    
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
                    9. View Course by Code
                    10.View All Courses
                    ------------------------------
                    Enroll Manage:
                    11. Enroll Student in Course
                    12. Change Enrollment
                    13. Delete Enrollment
                    14. View Enrollment by ID
                    15. View All Enrollments
                    ------------------------------
                    16. Logout
                    """;
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();

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
                    updateStudent();
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
                if (age > 0) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer.");
                }
            } catch (NumberFormatException e){
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
                if (year > 0) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid year. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Student student = studentService.registerStudent(name, age, year);
        if (student != null) {
            System.out.println("Student registered successfully:");
            System.out.println(student);
        } else {
            System.out.println("Failed to register student.");
        }
    }

    private static void updateStudent(){
        viewAllStudents();
        String studentId = checkStudentId().getStudentId();

        if(studentId != null){
            updateStudentById(studentId);
        }
    }

    private static void updateStudentById(String studentId) {
        String name = null;
        int age = 0;
        int year = 0;
        boolean isValid = false;
        Student studentOld = studentService.findStudentById(studentId);
        System.out.println("Name: " + studentOld.getName());
        System.out.println("Age: " + studentOld.getAge() + ", Year: " + studentOld.getYear());
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
                if (age > 0) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer.");
                }
            } catch (NumberFormatException e){
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
                if (year > 0) {
                    isValid = true;
                } else {
                    throw new InvalidStudentFormatException("Age must be a positive integer.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid year. Try again!.");
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Student student = studentService.reStudent(studentId, name, age, year);
        System.out.println("----------------------");
        System.out.println("Update Success  ");
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge() + " Year: " + student.getYear());
    }

    private static Student checkStudentId(){
        String studentId;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter student ID: ");
                studentId = scanner.nextLine();
                if (studentId.trim().isEmpty()) {
                    throw new InvalidStudentFormatException("Student ID cannot be empty");
                }
                Student student = studentService.findStudentById(studentId);
                if (student != null){
                    isValid = true;
                    return student;
                } else {
                    System.out.println("Not Found student ID. Try again!.");
                    isValid = true;
                }
            } catch (InvalidStudentFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private static void deleteStudent() {
        viewAllStudents();
        String studentId = checkStudentId().getStudentId();
        if(studentId != null){
            Student student = studentService.deleteStudent(studentId);
            if (student != null) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student.");
            }
        }
    }

    private static void findStudentById() {
        String studentId = checkStudentId().getStudentId();
        Student student = studentService.findStudentById(studentId);
        if(student != null){
            System.out.println("Student found: ");
            System.out.println("Name: " + student.getName());
            System.out.println("Age: " + student.getAge() + " Year: " + student.getYear());
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void viewAllStudents() {
        Stream<Student> studentStream = studentService.getAllStudent();
        if (studentStream.count() > 0) {
            studentStream = studentService.getAllStudent();
            System.out.println("All students:");
            studentStream.forEach(System.out::println);
        } else {
            System.out.println("No students available.");
        }
    }
    // Manage Enroll UI

    private static void enrollStudentInCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course codes (comma separated): ");
        String[] courseCodes = scanner.nextLine().split(",");
        Course[] courses = new Course[courseCodes.length];
        for (int i = 0; i < courseCodes.length; i++) {
            courses[i] = studentService.getCourseByCode(courseCodes[i].trim());
            if (courses[i] == null) {
                System.out.println("Course with code " + courseCodes[i].trim() + " not found.");
                return;
            }
        }
        Enrollment enrollment = studentService.enrollStudentInCourse(studentId, courses);
        System.out.println("Enrolled in courses: " + enrollment);
    }

    private static void enrollStudentInCourse(String studentId) {
        Enrollment checkEnroll = studentService.findEnrollmentByStudentId(studentId);

        if (checkEnroll != null) {
            System.out.println("You has enrolled already!");
            System.out.println(checkEnroll);
            return;
        }

        System.out.println("Enroll in Courses.");
        List<Course> availableCourses = new ArrayList<>();
        studentService.getAllCourses().forEach(availableCourses::add);

        List<Course> selectedCourses = new ArrayList<>();

        while (true) {
            System.out.println("List All Courses:");
            System.out.println("Type number to select option.");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ". " + availableCourses.get(i));
            }
            System.out.println("Select option (0 to exit):");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                break;
            } else if (option > 0 && option <= availableCourses.size()) {
                Course selectedCourse = availableCourses.get(option - 1);

                if (!selectedCourses.contains(selectedCourse)) {
                    selectedCourses.add(selectedCourse);
                    System.out.println("Course " + selectedCourse.getCourseCode() + " added.");
                } else {
                    System.out.println("You have already selected this course.");
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        if (!selectedCourses.isEmpty()) {
            Enrollment enrollment = studentService.enrollStudentInCourse(studentId, selectedCourses.toArray(new Course[0]));
            System.out.println("Enrolled in courses: " + enrollment);
        } else {
            System.out.println("No courses Enrolled.");
        }
    }

    private static void changeEnrollment() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.println("Enroll in Courses.");

        Enrollment studentEnroll = studentService.findEnrollmentByStudentId(studentId);
        System.out.println("Student Enroll: " + studentEnroll);

        List<Course> availableCourses = new ArrayList<>();
        studentService.getAllCourses().forEach(availableCourses::add);

        List<Course> selectedCourses = new ArrayList<>();

        while (true) {
            System.out.println("List All Courses:");
            System.out.println("Type number to select option.");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ". " + availableCourses.get(i));
            }
            System.out.println("Select option (0 to exit):");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                break;
            } else if (option > 0 && option <= availableCourses.size()) {
                Course selectedCourse = availableCourses.get(option - 1);
                if (!selectedCourses.contains(selectedCourse)) {
                    selectedCourses.add(selectedCourse);
                    System.out.println("Course " + selectedCourse.getCourseCode() + " added.");
                } else {
                    System.out.println("You have already selected this course.");
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        if (!selectedCourses.isEmpty()) {
            Enrollment enrollment = studentService.enrollStudentInCourse(studentId, selectedCourses.toArray(new Course[0]));
            System.out.println("Enrolled in courses: " + enrollment);
        } else {
            studentService.enrollStudentInCourse(studentId, selectedCourses.toArray(new Course[0]));
            System.out.println("No courses Enrolled.");
        }
    }

    private static void deleteEnrollment() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Enrollment enrollment = studentService.deleteEnrollment(studentId);
        if (enrollment != null) {
            System.out.println("Enrollment deleted successfully.");
        } else {
            System.out.println("Failed to delete enrollment.");
        }
    }

    private static void viewEnrollment() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Enrollment enrollment = studentService.findEnrollmentByStudentId(studentId);
        if (enrollment != null) {
            System.out.println("Enrollment found:");
            System.out.println(enrollment);
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    private static void findEnrollmentByStudentId(String studentId) {
        Enrollment enrollment = studentService.findEnrollmentByStudentId(studentId);
        if (enrollment != null) {
            System.out.println("Your Enrollment:");
            System.out.println(enrollment);
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    private static void addCourse() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        Course course = studentService.addCourse(courseCode, courseName, credits);
        if (course != null) {
            System.out.println("Course added successfully:");
            System.out.println(course);
        } else {
            System.out.println("Failed to add course.");
        }
    }

    private static void updateCourse() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter new course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter new credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        Course course = studentService.updateCourse(courseCode, courseName, credits);
        if (course != null) {
            System.out.println("Course updated successfully:");
            System.out.println(course);
        } else {
            System.out.println("Failed to update course.");
        }
    }

    private static void deleteCourse() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = studentService.deleteCourse(courseCode);
        if (course != null) {
            System.out.println("Course deleted successfully.");
        } else {
            System.out.println("Failed to delete course.");
        }
    }

    private static void viewCourseByCode() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = studentService.getCourseByCode(courseCode);
        if (course != null) {
            System.out.println("Course found:");
            System.out.println(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void viewAllCourses() {
        Stream<Course> courses = studentService.getAllCourses();
        if (courses.count() > 0) {
            courses = studentService.getAllCourses();
            System.out.println("All courses:");
            courses.forEach(System.out::println);
        } else {
            System.out.println("No courses available.");
        }
    }

    private static void viewAllEnrollments() {
        Stream<Enrollment> enrollments = studentService.getAllEnrollment();
        if (enrollments.count() > 0) {
            enrollments = studentService.getAllEnrollment();
            System.out.println("All enrollments:");
            enrollments.forEach(System.out::println);
        } else {
            System.out.println("No enrollments available.");
        }
    }
}
