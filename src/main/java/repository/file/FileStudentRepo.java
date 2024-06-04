package repository.file;

import entities.Student;
import repository.StudentManagement;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FileStudentRepo implements StudentManagement {
    private String PATH = "C:\\Users\\User\\Documents\\INT103\\studentSystemManagement\\repoFile\\";
    private String filename = PATH + "student.dat";
    private long nextStudentId = 0;
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
            repo = new TreeMap<>();
        }
    }

    private void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeLong(nextStudentId);
            oos.writeObject(repo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student addStudent(String name, int age, int year) {
        String studentId = "S66" + ++nextStudentId;
        Student s = new Student(studentId, name, age, year);
        if (repo.putIfAbsent(studentId, s) == null) {
            writeToFile();
            return s;
        }
        return null;
    }

    @Override
    public Student updateStudent(String studentId, Student s) {
        try {
            repo.put(s.getStudentId(), s);
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
