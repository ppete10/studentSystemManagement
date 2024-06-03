package repository;

import entities.Enrollment;
import entities.Student;

import java.util.Collection;

public interface StudentManagement {
    Student addStudent(String firstName,String lastName,int age, int year);
    Student updateStudent(String studentId,Student newStudent);
    Student deleteStudent(Student student);
    Student findStudentById(String studentId);
    Collection<Student> getAllStudent();

}
