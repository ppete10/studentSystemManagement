package repository.memory;

import entities.Student;
import repository.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class MemStudentRepo implements StudentManagement {
    private static long nextId = 0;
    private Map<String, Student> repo = new HashMap<>();

    @Override
    public Student addStudent(String name, int age, int year) {
        String studentId = "S66" + String.format("%03d", ++nextId);
        Student s = new Student(studentId, name, age, year);
        if (repo.putIfAbsent(studentId, s) == null)
            return s;
        return null;
    }

    @Override
    public Student updateStudent(Student s) {
        try {
            repo.replace(s.getStudentId(), s);
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
    public Stream<Student> getAllStudent() {
        return repo.values().stream();
    }
}
