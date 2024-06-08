package entities;


import exception.InvalidStudentFormatException;

import java.io.Serializable;

public class Student implements Serializable {
    private final String studentId;
    private String name;
    private int age;
    private int year;

    public Student(String studentId, String name, int age, int year) {
        if (studentId == null || studentId.isBlank() ||
                name == null || name.isBlank() || age <= 0 || year <= 0)
            throw new InvalidStudentFormatException();
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int semester) {
        this.year = semester;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-5d %-5d", studentId, name, age, year);
    }
}
