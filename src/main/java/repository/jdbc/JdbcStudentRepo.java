package repository.jdbc;

import entities.Student;
import repository.StudentManagement;

import java.util.Collection;

public class JdbcStudentRepo implements StudentManagement {
    @Override
    public Student addStudent(String name, int age, int year) {
        return null;
    }

    @Override
    public Student updateStudent(String studentId, Student newStudent) {
        return null;
    }

    @Override
    public Student deleteStudent(Student student) {
        return null;
    }

    @Override
    public Student findStudentById(String studentId) {
        return null;
    }

    @Override
    public Collection<Student> getAllStudent() {
        return null;
    }
}
