package repository.file;

import entities.Course;
import entities.Enrollment;
import repository.EnrollManagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static repository.file.FileStudentRepo.PATH;

public class FileEnrollmentRepo implements EnrollManagement {
    private final String filename = PATH + "enrollment.dat";
    private static long nextCode = 0;
    private final Map<String, Enrollment> enrollRepo;

    public FileEnrollmentRepo() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fi = new FileInputStream(file);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {

                nextCode = oi.readLong();
                enrollRepo = (HashMap<String, Enrollment>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("File not found");
            }
        } else {
            nextCode = 1;
            enrollRepo = new HashMap<>();
        }
    }

    private void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeLong(nextCode);
            oos.writeObject(enrollRepo);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to file");
        }
    }

    @Override
    public Enrollment addEnrollment(String enrollId, Set<Course> course) {
        Enrollment e = new Enrollment(enrollId);
        for (Course c : course) {
            e.addCourse(c);
        }
        enrollRepo.put(e.getStudentEnrollId(), e);
        writeToFile();
        return e;
    }

    @Override
    public Enrollment updateEnrollment(Enrollment e, Set<Course> course) {
        e.updateCourse(course);
        enrollRepo.replace(e.getStudentEnrollId(), e);
        writeToFile();
        return e;
    }

    @Override
    public Enrollment deleteEnrollment(Enrollment enrollId) {
        try {
            enrollRepo.remove(enrollId.getStudentEnrollId(), enrollId);
        } catch (Exception e) {
            return null;
        }
        writeToFile();
        return enrollId;
    }

    @Override
    public Enrollment getEnrollmentByStudentId(String studentEnrollId) {
        return enrollRepo.get(studentEnrollId);
    }

    @Override
    public Stream<Enrollment> getAllEnrollment() {
        return enrollRepo.values().stream();
    }
}
