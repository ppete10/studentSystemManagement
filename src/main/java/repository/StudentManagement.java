package repository;

import entities.Student;

import java.util.stream.Stream;

public interface StudentManagement {
    Student addStudent(String name, int age, int year);

    Student updateStudent(Student newStudent);

    Student deleteStudent(Student student);

    Student findStudentById(String studentId);

    Stream<Student> getAllStudent();

}
