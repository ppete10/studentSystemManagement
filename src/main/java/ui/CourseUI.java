package ui;

import entities.Course;
import exception.InvalidCourseFormatException;

import java.util.stream.Stream;

import static ui.MainUI.scanner;
import static ui.MainUI.systemServices;

public class CourseUI {

    //manage Course UI
    static void addCourse() {
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
                    throw new InvalidCourseFormatException("Credits cannot be empty.");
                }
                credits = Integer.parseInt(input);
                if (credits > 0) {
                    Course course = systemServices.addCourse(courseCode, courseName, credits);
                    if (course != null) {
                        System.out.println("              Course added successfully");
                        System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
                        System.out.println("---------------------------------------------------------");
                        System.out.println(course.toStringFormat());
                        System.out.println("---------------------------------------------------------");
                    } else {
                        System.out.println("Failed to add course.");
                    }
                    isValid = true;
                } else {
                    throw new InvalidCourseFormatException("Credits must be a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid credits. Try again!.");
            } catch (InvalidCourseFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void updateCourse() {
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
        System.out.println("                     Old Course");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
        System.out.println(c.toStringFormat());
        System.out.println("---------------------------------------------------------");

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
                    throw new InvalidCourseFormatException("Credits cannot be empty.");
                }
                credits = Integer.parseInt(input);
                if (credits > 0) {
                    isValid = true;
                } else {
                    throw new InvalidCourseFormatException("Credits must be a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid credits. Try again!.");
            } catch (InvalidCourseFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        Course course = systemServices.updateCourse(courseCode, courseName, credits);
        if (course != null) {
            System.out.println("               Course updated successfully");
            System.out.printf("%-7s %-30s %-3s%n", "Code", "Name", "Credits");
            System.out.println("---------------------------------------------------------");
            System.out.println(course.toStringFormat());
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("Failed to update course.");
        }
    }

    static void deleteCourse() {
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

    static void viewCourseByCode() {
        String courseCode;

        System.out.println("====== View Course By Code ======");
        while (true) {
            try {
                System.out.print("Enter Course Code or Exit[0]: ");
                courseCode = scanner.nextLine().toUpperCase();
                if (courseCode.isEmpty() || courseCode.isBlank()) {
                    throw new InvalidCourseFormatException("Course Code cannot be empty");
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
                    return;
                } else {
                    System.out.println("Course not found.");
                }
            } catch (InvalidCourseFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void viewAllCourses() {
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
