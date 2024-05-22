package ui;

import entities.Course;
import entities.Student;
import repository.MemCourseRepo;
import repository.MemEnrollmentRepo;
import repository.MemStudentRepo;
import service.StudentService;

import java.util.Scanner;

public class Main {
    private static StudentService studentService;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        studentService = new StudentService(new MemStudentRepo(), new MemEnrollmentRepo(), new MemCourseRepo());

        run();
    }

    private static void run() {
        System.out.println("Welcome to Student System Management");
        while (true) {
            System.out.println("Login for Student[1] or Teacher[2]? (Enter '0' to quit)");
            Integer role = scanner.nextInt();
            scanner.nextLine();

            if (role == 0) {
                break;
            } else if (role == 1) {
                handleStudentActions();
            } else if (role == 2) {
//                handleTeacherActions();
            } else {
                System.out.println("Invalid option. Please select Student[1] or Teacher[2].");
            }
        }
    }

    private static void handleStudentActions() {
        System.out.print("=== Registering a student. ===\n");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        Student student = studentService.registerStudent(firstName, lastName, age, year);
        if (student != null) {
            System.out.println("Student registered successfully:");
            System.out.println(student);
        } else {
            System.out.println("Failed to register student.");
        }
        String studentId = student.getStudentId();

        while (true) {
            String menu = """
                    \n===== Menu For Student=====
                    1. Show your Info
                    2. Update Info
                    3. View All Courses
                    4. Enrollment
                    5. View your Enrollment
                    6. Update Enrollment
                    7. Logout""";
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    student = studentService.findStudentById(studentId);
                    System.out.println("Found: " + student);
                    break;
                case 2:
                    System.out.println("Update Your Info.");
                    System.out.print("Enter first name: ");
                    firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    lastName = scanner.nextLine();
                    System.out.print("Enter age: ");
                    age = scanner.nextInt();
                    System.out.print("Enter year: ");
                    year = scanner.nextInt();
                    scanner.nextLine();
                    Student s = studentService.reStudent(studentId,firstName, lastName, age, year);
                    break;
                case 3:
                    System.out.println("All Courses: " + studentService.getAllCourses());
                    break;
                case 4:
                    System.out.println("Available courses:");
                    Course course = studentService.getAllCourses();
                    for (Course course : Course.values()) {
                        System.out.println(course.ordinal() + 1 + ". " + course.getCourseName());
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }

        }
    }

    private static Student registerStudent(Scanner scanner, StudentService studentService) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        Student student = studentService.registerStudent(firstName, lastName, age, year);
        if (student != null) {
            System.out.println("Student registered successfully:");
            System.out.println(student);
            return student;
        } else {
            System.out.println("Failed to register student.");
            return null;
        }
    }

}