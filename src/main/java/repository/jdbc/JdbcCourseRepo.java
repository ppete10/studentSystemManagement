package repository.jdbc;

import entities.Course;
import exception.InvalidCourseFormatException;
import repository.CourseMangement;

import java.sql.*;
import java.util.stream.Stream;

public class JdbcCourseRepo implements CourseMangement {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public Course addCourse(String courseCode, String courseName, int credits) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String insertCourseQuery = "INSERT INTO courses (course_code, course_name, credits) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertCourseQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, courseCode);
                stmt.setString(2, courseName);
                stmt.setInt(3, credits);
                stmt.executeUpdate();
                return new Course(courseCode, courseName, credits);
            }
        } catch (SQLException | InvalidCourseFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course updateCourse(String courseCode, Course course) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String updateCourseQuery = "UPDATE courses SET course_name = ?, credits = ? WHERE course_code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateCourseQuery)) {
                stmt.setString(1, course.getCourseName());
                stmt.setInt(2, course.getCredits());
                stmt.setString(3, courseCode);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating course failed, no rows affected.");
                }
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course deleteCourse(Course course) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String deleteCourseQuery = "DELETE FROM courses WHERE course_code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteCourseQuery)) {
                stmt.setString(1, course.getCourseCode());
                stmt.executeUpdate();
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectCourseQuery = "SELECT * FROM courses WHERE course_code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(selectCourseQuery)) {
                stmt.setString(1, courseCode);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    int credits = resultSet.getInt("credits");
                    return new Course(courseCode, courseName, credits);
                } else {
                    return null;
                }
            }
        } catch (SQLException | InvalidCourseFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Stream<Course> getAllCourses() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectAllCoursesQuery = "SELECT * FROM courses";
            try (PreparedStatement stmt = conn.prepareStatement(selectAllCoursesQuery)) {
                ResultSet resultSet = stmt.executeQuery();
                Stream.Builder<Course> courseStreamBuilder = Stream.builder();
                while (resultSet.next()) {
                    String courseCode = resultSet.getString("course_code");
                    String courseName = resultSet.getString("course_name");
                    int credits = resultSet.getInt("credits");
                    courseStreamBuilder.add(new Course(courseCode, courseName, credits));
                }
                return courseStreamBuilder.build();
            }
        } catch (SQLException | InvalidCourseFormatException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }
}