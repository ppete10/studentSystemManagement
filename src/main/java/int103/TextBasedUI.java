package int103;

import entities.*;
import repository.*;
import service.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class TextBasedUI {

    public static void main(String[] args) {
        StudentService studentService = new StudentService(new MemStudentRepo(), new MemEnrollmentRepo());
        studentService.setGradeCalculation(new MemGradeCalculationRepo());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Student System Management ===");
            System.out.println("1. Register Student");
            System.out.println("2. Enroll Student");
            System.out.println("3. List All Students");
            System.out.println("4. List All Enrollments");
            System.out.println("5. Add Grade");
            System.out.println("6. Calculate GPA");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    registerStudent(scanner, studentService);
                    break;
                case 2:
                    enrollStudent(scanner, studentService);
                    break;
                case 3:
                    listAllStudents(studentService);
                    break;
                case 4:
                    listAllEnrollments(studentService);
                    break;
                case 5:
                    addGrade(scanner, studentService);
                    break;
                case 6:
                    calculateGPA(scanner, studentService);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerStudent(Scanner scanner, StudentService studentService) {
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
    }

    private static void enrollStudent(Scanner scanner, StudentService studentService) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.println("Available courses:");
        for (Course course : Course.values()) {
            System.out.println(course.ordinal() + 1 + ". " + course.getCourseName());
        }

        List<Course> courses = new ArrayList<>();
        while (true) {
            System.out.print("Enter course number to enroll (0 to finish): ");
            int courseNumber = scanner.nextInt();
            scanner.nextLine();
            if (courseNumber == 0) break;
            courses.add(Course.values()[courseNumber - 1]);
        }

        Enrollment enrollment = studentService.enrollment(studentId, courses.toArray(new Course[0]));
        if (enrollment != null) {
            System.out.println("Enrollment successful for student ID: " + studentId);
            System.out.println("Enrolled in courses: " + enrollment.getCourse());
        } else {
            System.out.println("Failed to enroll student.");
        }
    }

    private static void listAllStudents(StudentService studentService) {
        Collection<Student> students = studentService.getAllStudent();
        System.out.println("\nAll Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void listAllEnrollments(StudentService studentService) {
        Collection<Enrollment> enrollments = studentService.getAllEnrollment();
        System.out.println("\nAll Enrollments:");
        for (Enrollment enrollment : studentService.getAllEnrollment()) {
            System.out.println(enrollment);
        }
    }

    private static void addGrade(Scanner scanner, StudentService studentService) {
        System.out.print("Enter enrollment ID: ");
        String enrollmentId = scanner.nextLine();
        System.out.print("Enter grade: ");
        double gradeValue = scanner.nextDouble();
        scanner.nextLine();

        Grade grade = new Grade(enrollmentId, gradeValue);
        System.out.println("Grade added: " + grade);
    }

    private static void calculateGPA(Scanner scanner, StudentService studentService) {
        Collection<Grade> grades = new ArrayList<>();
        Collection<Enrollment> enrollments = studentService.getAllEnrollment();

        System.out.println("Enter grades for enrollments:");
        for (Enrollment enrollment : enrollments) {
            System.out.print("Enter grade for " + enrollment.getStudentEnrollId() + ": ");
            double gradeValue = scanner.nextDouble();
            scanner.nextLine();
            grades.add(new Grade(enrollment.getStudentEnrollId(), gradeValue));
        }

        double gpa = studentService.calculateGPA(grades, enrollments);
        System.out.println("Calculated GPA: " + gpa);
    }
}
