package service;

import entities.Course;
import entities.Enrollment;

import java.util.Collection;

public interface EnrollManagement {
    Enrollment addEnrollment(String enrollId, Course... course);
    Enrollment updateEnrollment(Enrollment studentEnrollId, Course[] course);
    public Enrollment deleteEnrollment(Enrollment enrollId);
    Enrollment getEnrollmentByStudentId(String studentEnrollId);
    Collection<Enrollment> getAllEnrollment();
}
