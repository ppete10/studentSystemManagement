package repository;

import entities.Enrollment;
import entities.Student;

import java.util.Collection;
import java.util.stream.Stream;

public interface StudentManagement {
    Student addStudent(String name,int age, int year);
    Student updateStudent(String studentId,Student newStudent);
    Student deleteStudent(Student student);
    Student findStudentById(String studentId);
    Stream<Student> getAllStudent();

}
