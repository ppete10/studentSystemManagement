package repository.jdbc;

import entities.Course;
import repository.CourseMangement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JdbcCourseRepo implements CourseMangement {

    public JdbcCourseRepo() {
        createCourseTable();
    }

    private void createCourseTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Course", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Course ("
                        + "courseId VARCHAR(10) PRIMARY KEY,"
                        + "courseName VARCHAR(100) NOT NULL,"
                        + "credits INT"
                        + ")";
                stmt.executeUpdate(createTableSQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Course addCourse(String courseId, String courseName, int credits) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Course (courseId, courseName, credits) VALUES (?, ?, ?)")) {

            stmt.setString(1, courseId);
            stmt.setString(2, courseName);
            stmt.setInt(3, credits);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating course failed.");
            }

            return new Course(courseId, courseName, credits);
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Course updateCourse(String courseCode, Course course) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Course SET courseId=?, courseName=?, credits=? WHERE courseId=?")) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            stmt.setString(4, courseCode);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating course failed.");
            }

            return course;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Course deleteCourse(Course course) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Course WHERE courseId=?")) {

            stmt.setString(1, course.getCourseCode());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting course failed.");
            }

            return course;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course WHERE courseId=?")) {

            stmt.setString(1, courseCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String courseName = rs.getString("courseName");
                    int credits = rs.getInt("credits");
                    return new Course(courseCode, courseName, credits);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Stream<Course> getAllCourses() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Course")) {

            List<Course> courses = new ArrayList<>();
            while (rs.next()) {
                String courseCode = rs.getString("courseId");
                String courseName = rs.getString("courseName");
                int credits = rs.getInt("credits");
                courses.add(new Course(courseCode, courseName, credits));
            }
            return courses.stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }
}
