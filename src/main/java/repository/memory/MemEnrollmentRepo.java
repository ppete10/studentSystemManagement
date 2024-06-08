package repository.memory;

import entities.Course;
import entities.Enrollment;
import repository.EnrollManagement;


import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class MemEnrollmentRepo implements EnrollManagement {
    private static long nextCode = 0;
    private Map<String, Enrollment> enrollRepo;
    @Override
    public Enrollment addEnrollment(String enrollId, Set<Course> c) {
        Enrollment e = new Enrollment(enrollId);
        enrollRepo.put(e.getStudentEnrollId(), e);
        return e;
    }

    @Override
    public Enrollment updateEnrollment(Enrollment e, Set<Course> course) {
        e.updateCourse(course);
        enrollRepo.replace(e.getStudentEnrollId(), e);
        return e;
    }

    @Override
    public Enrollment deleteEnrollment(Enrollment enrollId) {
        try {
            enrollRepo.remove(enrollId.getStudentEnrollId(), enrollId);
        } catch (Exception e) {
            return null;
        }
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
