package repository.jdbc;

import entities.Course;
import entities.Enrollment;
import repository.EnrollManagement;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class JdbcEnrollmentRepo implements EnrollManagement {


    public JdbcEnrollmentRepo() {
        createEnrollmentTable();
    }

    private void createEnrollmentTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Enrollment", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Enrollment ("
                        + "enrollment_id VARCHAR(10),"
                        + "course_code VARCHAR(10),"
                        + "course_name VARCHAR(100),"
                        + "credits INT"
                        + ")";
                stmt.executeUpdate(createTableSQL);
            }
        } catch (SQLException e) {

        }
    }


    @Override
    public Enrollment addEnrollment(String enrollId, Set<Course> courses) {
        try (Connection conn = DatabaseConnection.getConnection();

             PreparedStatement courseStmt = conn.prepareStatement("INSERT INTO Enrollment (enrollment_id, course_code, course_name, credits) VALUES (?, ?, ?, ?)")) {

            for (Course course : courses) {
                courseStmt.setString(1, enrollId);
                courseStmt.setString(2, course.getCourseCode());
                courseStmt.setString(3, course.getCourseName());
                courseStmt.setInt(4, course.getCredits());
                courseStmt.executeUpdate();
            }


            return new Enrollment(enrollId);
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Enrollment updateEnrollment(Enrollment e, Set<Course> courses) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteCourseStmt = conn.prepareStatement("DELETE FROM Enrollment WHERE enrollment_id = ?");
             PreparedStatement insertCourseStmt = conn.prepareStatement("INSERT INTO Enrollment (enrollment_id, course_code, course_name, credits) VALUES (?, ?, ?, ?)")) {


            // Delete existing enrollment courses
            deleteCourseStmt.setString(1, e.getStudentEnrollId());
            deleteCourseStmt.executeUpdate();

            // Insert new enrollment courses
            for (Course course : courses) {
                insertCourseStmt.setString(1, e.getStudentEnrollId());
                insertCourseStmt.setString(2, course.getCourseCode());
                insertCourseStmt.setString(3, course.getCourseName());
                insertCourseStmt.setInt(4, course.getCredits());
                insertCourseStmt.executeUpdate();
            }

            // Return the updated Enrollment object
            return e;
        } catch (SQLException ex) {

            return null;
        }
    }

    @Override
    public Enrollment deleteEnrollment(Enrollment e) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteCourseStmt = conn.prepareStatement("DELETE FROM Enrollment WHERE enrollment_id = ?")) {

            // Delete enrollment courses
            deleteCourseStmt.setString(1, e.getStudentEnrollId());
            deleteCourseStmt.executeUpdate();

            return e;
        } catch (SQLException ex) {

            return null;
        }
    }

    @Override
    public Enrollment getEnrollmentByStudentId(String studentEnrollId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT enrollment_id, course_code, course_name, credits FROM Enrollment WHERE enrollment_id = ?")) {

            stmt.setString(1, studentEnrollId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Enrollment enrollment = new Enrollment(studentEnrollId);
            do {
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                int credits = rs.getInt("credits");

                Course course = new Course(courseCode, courseName, credits);
                enrollment.addCourse(course);
            } while (rs.next());

            return enrollment;
        } catch (SQLException ex) {

            return null;
        }
    }

    @Override
    public Stream<Enrollment> getAllEnrollment() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT enrollment_id, course_code, course_name, credits FROM Enrollment")) {

            ResultSet rs = stmt.executeQuery();
            Map<String, Enrollment> enrollRepo = new HashMap<>();


            while (rs.next()) {
                String enrollId = rs.getString("enrollment_id");
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                int credits = rs.getInt("credits");

                Course course = new Course(courseCode, courseName, credits);

                Enrollment enroll = enrollRepo.getOrDefault(enrollId, new Enrollment(enrollId));
                enroll.addCourse(course);
                enrollRepo.put(enrollId, enroll);
            }

            return enrollRepo.values().stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }

}
