package repository.jdbc;

import entities.Course;
import entities.Enrollment;
import repository.EnrollManagement;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

public class JdbcEnrollmentRepo implements EnrollManagement {
    @Override
    public Enrollment addEnrollment(String enrollId, Course... course) {
        return null;
    }

    @Override
    public Enrollment updateEnrollment(Enrollment studentEnrollId, Set<Course> course) {
        return null;
    }

    @Override
    public Enrollment deleteEnrollment(Enrollment enrollId) {
        return null;
    }

    @Override
    public Enrollment getEnrollmentByStudentId(String studentEnrollId) {
        return null;
    }

    @Override
    public Stream<Enrollment> getAllEnrollment() {
        return null;
    }
}
