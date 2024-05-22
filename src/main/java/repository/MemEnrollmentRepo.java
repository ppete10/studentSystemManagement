package repository;

import entities.Course;
import entities.Enrollment;
import service.EnrollManagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemEnrollmentRepo implements EnrollManagement {
    private static long nextCode = 0;
    private Map<String, Enrollment> enrollRepo = new HashMap<>();
    @Override
    public Enrollment addEnrollment(String enrollId, Course... course) {
        Enrollment e = new Enrollment(enrollId);
        for(Course c : course){
            e.addCourse(c);
        }
        enrollRepo.put(e.getStudentEnrollId(), e);
        return e;
    }

    @Override
    public Enrollment updateEnrollment(Enrollment e, Course... course) {
        for(Course c : course){
            e.addCourse(c);
        }
        enrollRepo.put(e.getStudentEnrollId(), e);
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
    public Collection<Enrollment> getAllEnrollment() {
        return enrollRepo.values();
    }
}
