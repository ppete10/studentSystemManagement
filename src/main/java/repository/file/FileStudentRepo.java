package repository.file;

import entities.Student;
import repository.StudentManagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileStudentRepo implements StudentManagement {
    static final String PATH = ".\\repoFile\\";
    private final String filename = PATH + "student.dat";
    private long nextId = 0;
    private final Map<String, Student> repo;

    public FileStudentRepo() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fi = new FileInputStream(file);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {

                nextId = oi.readLong();
                repo = (HashMap<String, Student>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("File not found");
            }
        } else {
            repo = new HashMap<>();
        }
    }

    private void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeLong(nextId);
            oos.writeObject(repo);
        } catch (IOException e) {
            throw new RuntimeException("Can not write to file");
        }
    }

    @Override
    public Student addStudent(String name, int age, int year) {
        String studentId = "S66" + String.format("%03d", ++nextId);
        Student s = new Student(studentId, name, age, year);
        if (repo.putIfAbsent(studentId, s) == null) {
            writeToFile();
            return s;
        }
        return null;
    }

    @Override
    public Student updateStudent(Student s) {
        try {
            repo.replace(s.getStudentId(), s);
        } catch (Exception e) {
            return null;
        }
        writeToFile();
        return s;
    }

    @Override
    public Student deleteStudent(Student student) {
        try {
            repo.remove(student.getStudentId(), student);
        } catch (Exception e) {
            return null;
        }
        writeToFile();
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
