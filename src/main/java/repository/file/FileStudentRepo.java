package repository.file;

import entities.Student;
import repository.StudentManagement;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class FileStudentRepo implements StudentManagement {
    private String filename = "student.dat";
    private long nextStudentId;
    private Map<String, Student> repo;

    public FileStudentRepo(){
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fi = new FileInputStream(file);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {

                nextStudentId = oi.readLong();
                repo = (TreeMap<String, Student>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            nextStudentId = 1;
            repo = new TreeMap<>();
        }
    }

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
