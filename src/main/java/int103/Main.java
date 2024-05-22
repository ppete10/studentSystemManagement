package int103;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import repository.MemCourseRepo;
import repository.MemEnrollmentRepo;
import repository.MemStudentRepo;
import service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
                handleTeacherActions();
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
            return;
        }
        String studentId = student.getStudentId();

        while (true) {
            String menu = """
                    \n===== Menu For Student =====
                    1. Show your Info
                    2. Update Info
                    3. View All Courses
                    4. Enroll in Courses
                    5. View your Enrollments
                    6. Update Enrollment
                    7. Logout
                    """;
            System.out.println(menu);
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
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
                    student = studentService.reStudent(studentId, firstName, lastName, age, year);
                    System.out.println("Updated: " + student);
                    break;
                case 3:
                    System.out.println("All Courses: " + studentService.getAllCourses());
                    break;
                case 4:
                    System.out.println("Enroll in Courses.");
                    List<Course> availableCourses = new ArrayList<>(studentService.getAllCourses());
                    List<Course> selectedCourses = new ArrayList<>();
                    while (true) {
                        System.out.println("List All Courses:");
                        for (int i = 0; i < availableCourses.size(); i++) {
                            System.out.println((i + 1) + ". " + availableCourses.get(i));
                        }
                        System.out.println("Select option (0 to exit):");
                        int courseOption = scanner.nextInt();
                        scanner.nextLine();
                        if (courseOption == 0) {
                            break;
                        } else if (courseOption > 0 && courseOption <= availableCourses.size()) {
                            Course selectedCourse = availableCourses.get(courseOption - 1);
                            if (!selectedCourses.contains(selectedCourse)) {
                                selectedCourses.add(selectedCourse);
                                availableCourses.remove(selectedCourse);
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
                        System.out.println("No courses selected.");
                    }
                    break;
                case 5:
                    Enrollment e = studentService.findEnrollmentByStudentId(studentId);
                    System.out.println("Your Enrollments: " + e);
                    break;
                case 6:
                    System.out.println("Update Enrollment.");
                    availableCourses = new ArrayList<>(studentService.getAllCourses());
                    selectedCourses = new ArrayList<>();
                    while (true) {
                        System.out.println("List All Courses:");
                        for (int i = 0; i < availableCourses.size(); i++) {
                            System.out.println((i + 1) + ". " + availableCourses.get(i));
                        }
                        System.out.println("Select option (0 to exit):");
                        int courseOption = scanner.nextInt();
                        scanner.nextLine();
                        if (courseOption == 0) {
                            break;
                        } else if (courseOption > 0 && courseOption <= availableCourses.size()) {
                            Course selectedCourse = availableCourses.get(courseOption - 1);
                            if (!selectedCourses.contains(selectedCourse)) {
                                selectedCourses.add(selectedCourse);
                                availableCourses.remove(selectedCourse);
                                System.out.println("Course " + selectedCourse.getCourseCode() + " added.");
                            } else {
                                System.out.println("You have already selected this course.");
                            }
                        } else {
                            System.out.println("Invalid option. Please try again.");
                        }
                    }
                    if (!selectedCourses.isEmpty()) {
                        Enrollment enrollment = studentService.changeEnrollment(studentId, selectedCourses.toArray(new Course[0]));
                        System.out.println("Updated Enrollments: " + enrollment);
                    } else {
                        System.out.println("No courses selected.");
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void handleTeacherActions() {
        while (true) {
            String menu = """
                    \n===== Menu For Teacher =====
                    Student Manage:
                    1. Register Student
                    2. Update Student
                    3. Delete Student
                    4. Find Student by ID
                    Enroll Manage:
                    5. Enroll Student in Course
                    6. Change Enrollment
                    7. Delete Enrollment
                    8. View Enrollment
                    Course Manage:
                    9. Add Course
                    10. Update Course
                    11. Delete Course
                    12. View Course by Code
                    13. View All Courses
                    14. View All Students
                    15. View All Enrollments
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
                    enrollStudentInCourse();
                    break;
                case 6:
                    changeEnrollment();
                    break;
                case 7:
                    deleteEnrollment();
                    break;
                case 8:
                    viewEnrollment();
                    break;
                case 9:
                    addCourse();
                    break;
                case 10:
                    updateCourse();
                    break;
                case 11:
                    deleteCourse();
                    break;
                case 12:
                    viewCourseByCode();
                    break;
                case 13:
                    viewAllCourses();
                    break;
                case 14:
                    viewAllStudents();
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

    private static void registerStudent() {
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

    private static void updateStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        System.out.print("Enter new year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        Student student = studentService.reStudent(studentId, firstName, lastName, age, year);
        if (student != null) {
            System.out.println("Student updated successfully:");
            System.out.println(student);
        } else {
            System.out.println("Failed to update student.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentService.deleteStudent(studentId);
        if (student != null) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Failed to delete student.");
        }
    }

    private static void findStudentById() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentService.findStudentById(studentId);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

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

    private static void changeEnrollment() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter new course codes (comma separated): ");
        String[] courseCodes = scanner.nextLine().split(",");
        Course[] courses = new Course[courseCodes.length];
        for (int i = 0; i < courseCodes.length; i++) {
            courses[i] = studentService.getCourseByCode(courseCodes[i].trim());
            if (courses[i] == null) {
                System.out.println("Course with code " + courseCodes[i].trim() + " not found.");
                return;
            }
        }
        Enrollment enrollment = studentService.changeEnrollment(studentId, courses);
        System.out.println("Updated Enrollments: " + enrollment);
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
        Collection<Course> courses = studentService.getAllCourses();
        if (!courses.isEmpty()) {
            System.out.println("All courses:");
            for (Course course : courses) {
                System.out.println(course);
            }
        } else {
            System.out.println("No courses available.");
        }
    }

    private static void viewAllStudents() {
        Collection<Student> students = studentService.getAllStudent();
        if (!students.isEmpty()) {
            System.out.println("All students:");
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("No students available.");
        }
    }

    private static void viewAllEnrollments() {
        Collection<Enrollment> enrollments = studentService.getAllEnrollment();
        if (!enrollments.isEmpty()) {
            System.out.println("All enrollments:");
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }
        } else {
            System.out.println("No enrollments available.");
        }
    }
}
