package repository.jdbc;

import entities.Course;
import entities.Enrollment;
import repository.EnrollManagement;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class JdbcEnrollmentRepo implements EnrollManagement {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/university";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public Enrollment addEnrollment(String enrollId, Course... courses) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrollments (enrollment_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, enrollId);
            stmt.executeUpdate();

            Enrollment enrollment = new Enrollment(enrollId);
            for (Course course : courses) {
                enrollment.addCourse(course);
                try (PreparedStatement enrollmentStmt = conn.prepareStatement("INSERT INTO enrollment_courses (enrollment_id, course_id) VALUES (?, ?)")) {
                    enrollmentStmt.setString(1, enrollId);
                    enrollmentStmt.setString(2, course.getCourseCode());
                    enrollmentStmt.executeUpdate();
                }
            }
            return enrollment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Enrollment updateEnrollment(Enrollment enrollment, Set<Course> courses) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Delete existing enrollment courses
            try (PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM enrollment_courses WHERE enrollment_id = ?")) {
                deleteStmt.setString(1, enrollment.getStudentEnrollId());
                deleteStmt.executeUpdate();
            }

            // Insert new enrollment courses
            for (Course course : courses) {
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO enrollment_courses (enrollment_id, course_id) VALUES (?, ?)")) {
                    insertStmt.setString(1, enrollment.getStudentEnrollId());
                    insertStmt.setString(2, course.getCourseCode());
                    insertStmt.executeUpdate();
                }
            }

            enrollment.updateCourse(courses);
            return enrollment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Enrollment deleteEnrollment(Enrollment enrollment) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM enrollments WHERE enrollment_id = ?")) {

            stmt.setString(1, enrollment.getStudentEnrollId());
            stmt.executeUpdate();

            return enrollment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Enrollment getEnrollmentByStudentId(String studentEnrollId) {
        Enrollment enrollment = null;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM enrollments WHERE enrollment_id = ?")) {

            stmt.setString(1, studentEnrollId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    enrollment = new Enrollment(studentEnrollId);
                    Set<Course> courses = getCoursesForEnrollment(conn, studentEnrollId);
                    enrollment.updateCourse(courses);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollment;
    }

    @Override
    public Stream<Enrollment> getAllEnrollment() {
        // Not implemented as it's not clear how enrollments are structured in the database
        return null;
    }

    private Set<Course> getCoursesForEnrollment(Connection conn, String enrollmentId) throws SQLException {
        Set<Course> courses = new HashSet<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM enrollment_courses WHERE enrollment_id = ?")) {
            stmt.setString(1, enrollmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String courseId = rs.getString("course_id");
                    // Assuming there's a method to get a Course object by courseId
                    Course course = getCourseById(courseId);
                    if (course != null) {
                        courses.add(course);
                    }
                }
            }
        }
        return courses;
    }

    // You should implement a method to get Course object by courseId
    private Course getCourseById(String courseId) {
        // This method should retrieve the Course object from the database using the courseId
        // Implement this method according to your database schema
        return null;
    }
}
