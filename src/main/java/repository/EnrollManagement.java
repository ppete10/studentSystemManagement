package repository;

import entities.Course;
import entities.Enrollment;

import java.util.Set;
import java.util.stream.Stream;


public interface EnrollManagement {
    Enrollment addEnrollment(String enrollId, Set<Course> course);

    Enrollment updateEnrollment(Enrollment studentEnrollId, Set<Course> course);

    Enrollment deleteEnrollment(Enrollment enrollId);

    Enrollment getEnrollmentByStudentId(String studentEnrollId);

    Stream<Enrollment> getAllEnrollment();

}
