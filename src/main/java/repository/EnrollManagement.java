package repository;

import entities.Course;
import entities.Enrollment;

import java.util.stream.Stream;


public interface EnrollManagement {
    Enrollment addEnrollment(String enrollId, Course... course);
    Enrollment updateEnrollment(Enrollment studentEnrollId, Course[] course);
    Enrollment deleteEnrollment(Enrollment enrollId);
    Enrollment getEnrollmentByStudentId(String studentEnrollId);
    Stream<Enrollment> getAllEnrollment();
}
