package repository;

import entities.Student;
import service.StudentManagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class MemStudentRepo implements StudentManagement {
    private static long nextId = 0;
    private Map<String, Student> repo = new HashMap<>();

    @Override
    public Student addStudent(String firstName, String lastName, int age, int year) {
        String studentId = "S66" + ++nextId;
        Student s = new Student(studentId, firstName, lastName, age, year);
        if (repo.putIfAbsent(studentId, s) == null)
            return s;
        return null;
    }

    @Override
    public Student updateStudent(String studentId, Student s) {
        try {
            repo.put(s.getStudentId(), s);
        } catch (Exception e) {
            return null;
        }
        return s;
    }

    @Override
    public Student deleteStudent(Student student) {
        try {
            repo.remove(student.getStudentId(), student);
        } catch (Exception e) {
            return null;
        }
        return student;
    }

    @Override
    public Student findStudentById(String studentId) {
        return repo.get(studentId);
    }

    @Override
    public Collection<Student> getAllStudent() {
        return repo.values();
    }
}
