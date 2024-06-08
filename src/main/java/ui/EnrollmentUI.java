package ui;

import entities.Course;
import entities.Enrollment;
import entities.Student;
import exception.InvalidEnrollmentFormatException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static ui.MainUI.scanner;
import static ui.MainUI.systemServices;
import static ui.StudentUI.checkStudentId;
import static ui.StudentUI.viewAllStudents;

public class EnrollmentUI {

    // Manage Enroll UI
    static void enrollStudentInCourse() {
        viewAllStudents();
        if (systemServices.getAllStudent().findAny().isPresent()) {
            System.out.println("====== Enroll Student In Course ======");
            Student checkStudent = checkStudentId();

            if (checkStudent != null) {
                String studentId = checkStudent.getStudentId();
                enrollStudentInCourse(studentId);
            }

        } else {
            System.out.println("Enroll Student has Cancelled.");
        }
    }

    static void enrollStudentInCourse(String studentId) {
        Enrollment checkEnroll = systemServices.findEnrollmentByStudentId(studentId);

        if (checkEnroll != null) {
            System.out.println("                 Has enrolled already!");
            System.out.printf("%-6s %-7s %-30s %-3s%n", "ID", "Code", "Name", "Credits");
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
            System.out.printf("%-6s %-7s %-30s %-3s%n", "Number", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ".     " + availableCourses.get(i).toStringFormat());
            }
            if (availableCourses.isEmpty()) {
                System.out.println("             No Course Available.");
            }
            System.out.println("---------------------------------------------------------");
            System.out.println("If no courses are selected, it will be empty.");
            System.out.print("Type number (Choose one Number at a time) (0 to exit):");

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
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number. Please try again.");
            }
        }
        if (!selectedCourses.isEmpty()) {
            Set<Course> setCourse = new HashSet<>(selectedCourses);
            Enrollment enrollment = systemServices.enrollStudentInCourse(studentId, setCourse);
            System.out.println("                   Enroll In Courses Success.");
            System.out.printf("%-6s %-7s %-30s %-3s%n", "ID", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(enrollment);
            System.out.println("---------------------------------------------------------");
        } else {
            systemServices.enrollStudentInCourse(studentId, new HashSet<>());
            System.out.println("No courses Enrolled.");
        }
    }

    static void changeEnrollment() {
        viewAllEnrollments();
        try {
            Stream<Enrollment> any = systemServices.getAllEnrollment();
            if (any.findAny().isEmpty()) {
                System.out.println("Update Enroll has Cancelled.");
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("Update Enroll has Cancelled.");
            return;
        }

        System.out.println("====== Update Enrollment ======");
        Enrollment checkEnrollId = checkEnrollId();
        if (checkEnrollId == null) {
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
            System.out.printf("%-6s %-7s %-30s %-3s%n", "Number", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + ".     " + availableCourses.get(i).toStringFormat());
            }
            if (availableCourses.isEmpty()) {
                System.out.println("                 No Course Available.");
            }
            System.out.println("---------------------------------------------------------");
            System.out.println("This menu will replace all courses. If no courses are selected, nothing will be changes.");
            System.out.print("Type number (Choose one Number at a time) (0 to exit):");

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


                if (!selectedCourses.isEmpty()) {
                    Set<Course> setCourse = new HashSet<>(selectedCourses);
                    Enrollment enrollment = systemServices.changeEnrollment(enrollId, setCourse);
                    System.out.println("                 Update Enroll Success.");
                    System.out.printf("%-6s %-7s %-30s %-3s%n", "Number", "Code", "Name", "Credits");
                    System.out.println("---------------------------------------------------------");
                    System.out.println(enrollment);
                    System.out.println("---------------------------------------------------------");
                } else {
                    systemServices.changeEnrollment(enrollId, new HashSet<>());
                    System.out.println("No courses Enrolled.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number. Please try again.");
            }
        }
    }

    static void deleteEnrollment() {
        viewAllEnrollments();
        try {
            Stream<Enrollment> any = systemServices.getAllEnrollment();
            if (any.findAny().isEmpty()) {
                System.out.println("Delete Enrollment has Cancelled.");
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("Delete Enrollment has Cancelled.");
            return;
        }

        System.out.println("====== Delete Enrollment ======");
        Enrollment checkEnrollId = checkEnrollId();
        if (checkEnrollId == null) {
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

    private static Enrollment checkEnrollId() {
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

    static void viewEnrollment() {
        System.out.println("====== Find Enrollment By ID ======");
        Student checkStudent = checkStudentId();
        if (checkStudent == null) {
            System.out.println("Find Enrollment has Cancelled.");
            return;
        }
        String studentId = checkStudent.getStudentId();
        findEnrollmentByStudentId(studentId);
    }

    static void findEnrollmentByStudentId(String studentId) {
        Enrollment enrollment = systemServices.findEnrollmentByStudentId(studentId);
        if (enrollment != null) {
            System.out.println("                    Found Enrollment");
            System.out.printf("%-6s %-7s %-30s %-3s%n", "ID", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(enrollment);
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    static void viewAllEnrollments() {
        try {
            Stream<Enrollment> enrollments = systemServices.getAllEnrollment();
            if (enrollments.findAny().isPresent()) {
                enrollments = systemServices.getAllEnrollment();
                System.out.println("                   All Enrollments ");
                System.out.printf("%-6s %-7s %-30s %-3s%n", "ID", "Code", "Name", "Credits");
                System.out.println("---------------------------------------------------------");
                enrollments.forEach(System.out::println);
                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("No enrollments available.");
            }
        } catch (NullPointerException e) {
            System.out.println("No enrollments available.");
        }

    }
}
