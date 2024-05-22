package int103;

import entities.Course;
import entities.Enrollment;
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
        // Initialize repositories and services
        studentService = new StudentService(new MemStudentRepo(), new MemEnrollmentRepo(), new MemCourseRepo());

        // Run the application
        run();
    }

    private static void run() {
        System.out.println("Welcome to Student System Management");
        while (true) {
            System.out.println("Are you a Student or Teacher? (Enter 'exit' to quit)");
            String role = scanner.nextLine();
            if (role.equalsIgnoreCase("exit")) {
                break;
            } else if (role.equalsIgnoreCase("Student")) {
                handleStudentActions();
            } else if (role.equalsIgnoreCase("Teacher")) {
                handleTeacherActions();
            } else {
                System.out.println("Invalid role. Please enter 'Student' or 'Teacher'.");
            }
        }
    }

    private static void handleStudentActions() {
        System.out.println("Enter your student ID:");
        String studentId = scanner.nextLine();

        while (true) {
            System.out.println("Select an action: \n1. Register\n2. Update Info\n3. Find by ID\n4. Enroll in Course\n5. Change Enrollment\n6. View Enrollment\n7. View All Courses\n8. Logout");
            int action = Integer.parseInt(scanner.nextLine());

            switch (action) {
                case 1:
                    System.out.println("Enter first name, last name, age, year:");
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine());
                    int year = Integer.parseInt(scanner.nextLine());
                    Student student = studentService.registerStudent(firstName, lastName, age, year);
                    System.out.println("Registered: " + student);
                    break;
                case 2:
                    System.out.println("Enter new first name, last name, age, year:");
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    age = Integer.parseInt(scanner.nextLine());
                    year = Integer.parseInt(scanner.nextLine());
                    student = studentService.reStudent(studentId, firstName, lastName, age, year);
                    System.out.println("Updated: " + student);
                    break;
                case 3:
                    student = studentService.findStudentById(studentId);
                    System.out.println("Found: " + student);
                    break;
                case 4:
                    System.out.println("Enter course codes to enroll (comma separated):");
                    String[] courseCodes = scanner.nextLine().split(",");
                    Course[] courses = new Course[courseCodes.length];
                    for (int i = 0; i < courseCodes.length; i++) {
                        courses[i] = studentService.getCourseByCode(courseCodes[i]);
                    }
                    Enrollment enrollment = studentService.enrollStudentInCourse(studentId, courses);
                    System.out.println("Enrolled: " + enrollment);
                    break;
                case 5:
                    System.out.println("Enter new course codes (comma separated):");
                    courseCodes = scanner.nextLine().split(",");
                    courses = new Course[courseCodes.length];
                    for (int i = 0; i < courseCodes.length; i++) {
                        courses[i] = studentService.getCourseByCode(courseCodes[i]);
                    }
                    enrollment = studentService.changeEnrollment(studentId, courses);
                    System.out.println("Enrollment updated: " + enrollment);
                    break;
                case 6:
                    enrollment = studentService.findEnrollmentByStudentId(studentId);
                    System.out.println("Enrollment: " + enrollment);
                    break;
                case 7:
                    System.out.println("All Courses: " + studentService.getAllCourses());
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid action.");
            }
        }
    }

    private static void handleTeacherActions() {
        while (true) {
            System.out.println("Select an action: \n1. Register Student\n2. Update Student\n3. Delete Student\n4. Find Student by ID\n5. Enroll Student in Course\n6. Change Enrollment\n7. Delete Enrollment\n8. View Enrollment\n9. Add Course\n10. Update Course\n11. Delete Course\n12. View Course by Code\n13. View All Courses\n14. View All Students\n15. View All Enrollments\n16. Logout");
            int action = Integer.parseInt(scanner.nextLine());

            switch (action) {
                case 1:
                    System.out.println("Enter first name, last name, age, year:");
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine());
                    int year = Integer.parseInt(scanner.nextLine());
                    Student student = studentService.registerStudent(firstName, lastName, age, year);
                    System.out.println("Registered: " + student);
                    break;
                case 2:
                    System.out.println("Enter student ID, new first name, last name, age, year:");
                    String studentId = scanner.nextLine();
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    age = Integer.parseInt(scanner.nextLine());
                    year = Integer.parseInt(scanner.nextLine());
                    student = studentService.reStudent(studentId, firstName, lastName, age, year);
                    System.out.println("Updated: " + student);
                    break;
                case 3:
                    System.out.println("Enter student ID:");
                    studentId = scanner.nextLine();
                    student = studentService.deleteStudent(studentId);
                    System.out.println("Deleted: " + student);
                    break;
                case 4:
                    System.out.println("Enter student ID:");
                    studentId = scanner.nextLine();
                    student = studentService.findStudentById(studentId);
                    System.out.println("Found: " + student);
                    break;
                case 5:
                    System.out.println("Enter student ID and course codes to enroll (comma separated):");
                    studentId = scanner.nextLine();
                    String[] courseCodes = scanner.nextLine().split(",");
                    Course[] courses = new Course[courseCodes.length];
                    for (int i = 0; i < courseCodes.length; i++) {
                        courses[i] = studentService.getCourseByCode(courseCodes[i]);
                    }
                    Enrollment enrollment = studentService.enrollStudentInCourse(studentId, courses);
                    System.out.println("Enrolled: " + enrollment);
                    break;
                case 6:
                    System.out.println("Enter enrollment ID and new course codes (comma separated):");
                    String enrollmentId = scanner.nextLine();
                    courseCodes = scanner.nextLine().split(",");
                    courses = new Course[courseCodes.length];
                    for (int i = 0; i < courseCodes.length; i++) {
                        courses[i] = studentService.getCourseByCode(courseCodes[i]);
                    }
                    enrollment = studentService.changeEnrollment(enrollmentId, courses);
                    System.out.println("Enrollment updated: " + enrollment);
                    break;
                case 7:
                    System.out.println("Enter enrollment ID:");
                    enrollmentId = scanner.nextLine();
                    enrollment = studentService.deleteEnrollment(enrollmentId);
                    System.out.println("Deleted: " + enrollment);
                    break;
                case 8:
                    System.out.println("Enter enrollment ID:");
                    enrollmentId = scanner.nextLine();
                    enrollment = studentService.findEnrollmentByStudentId(enrollmentId);
                    System.out.println("Enrollment: " + enrollment);
                    break;
                case 9:
                    System.out.println("Enter course code, name, credits:");
                    String courseCode = scanner.nextLine();
                    String courseName = scanner.nextLine();
                    int credits = Integer.parseInt(scanner.nextLine());
                    Course course = studentService.addCourse(courseCode, courseName, credits);
                    System.out.println("Added: " + course);
                    break;
                case 10:
                    System.out.println("Enter course code, new name, credits:");
                    courseCode = scanner.nextLine();
                    courseName = scanner.nextLine();
                    credits = Integer.parseInt(scanner.nextLine());
                    course = studentService.updateCourse(courseCode, courseName, credits);
                    System.out.println("Updated: " + course);
                    break;
                case 11:
                    System.out.println("Enter course code:");
                    courseCode = scanner.nextLine();
                    course = studentService.getCourseByCode(courseCode);
                    course = studentService.deleteCourse(course.getCourseCode());
                    System.out.println("Deleted: " + course);
                    break;
                case 12:
                    System.out.println("Enter course code:");
                    courseCode = scanner.nextLine();
                    course = studentService.getCourseByCode(courseCode);
                    System.out.println("Found: " + course);
                    break;
                case 13:
                    System.out.println("All Courses: " + studentService.getAllCourses());
                    break;
                case 14:
                    System.out.println("All Students: " + studentService.getAllStudent());
                    break;
                case 15:
                    System.out.println("All Enrollments: " + studentService.getAllEnrollment());
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Invalid action.");
            }
        }
    }
}