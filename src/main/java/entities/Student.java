package entities;

import java.io.Serializable;

public class Student implements Serializable {
    private final String studentId;
    private String name;
    private int age;
    private int year;

    public Student(String studentId, String name, int age, int year ) {
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

    public int getSemester() {
        return year;
    }

    public void setSemester(int semester) {
        this.year = semester;
    }

    @Override
    public String toString() {
        return String.format("Student(%s,%s,%s,%d,%d)", studentId, name, age, year);
    }
}
