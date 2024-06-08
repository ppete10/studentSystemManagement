package ui;

import entities.Student;
import exception.InvalidStudentFormatException;

import java.util.stream.Stream;

import static ui.MainUI.scanner;
import static ui.MainUI.systemServices;

public class StudentUI {

    // Manage Student UI
    static void registerStudent() {
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
            System.out.println("   Student registered successfully");
            System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
            System.out.println("-------------------------------------");
            System.out.println(student);
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Failed to register student.");
        }
    }

    static void updateStudent() {
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

    static void updateStudentById(String studentId) {
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
        System.out.println("         Update Successfully");
        System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
        System.out.println("-------------------------------------");
        System.out.println(student);
        System.out.println("-------------------------------------");
    }

    static Student checkStudentId() {
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

    static void deleteStudent() {
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

    static void findStudentById() {
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

    static void viewAllStudents() {
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

    static void showYourInfo(String studentId) {
        Student student = systemServices.findStudentById(studentId);
        System.out.println("              Your Info");
        System.out.printf("%-6s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Year");
        System.out.println("-------------------------------------");
        System.out.println(student);
        System.out.println("-------------------------------------");
    }
}
