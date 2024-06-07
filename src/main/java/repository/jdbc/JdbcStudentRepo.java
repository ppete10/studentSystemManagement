package repository.jdbc;

import entities.Student;
import repository.StudentManagement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JdbcStudentRepo implements StudentManagement {
    //Access jdbc:odbc:dataSource
    //MySQL jdbc:mysql://hostname:port dbname
    //Oracle jdbc:oracle:thin//hostname:port:oracleDBSID
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentrepo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "030130";

    public JdbcStudentRepo() {
        //Access: sun.jdbc.odbc.JdbcOdbcDriver
        //MySQL: com.mysql.cj.jdbc.Driver
        //Oracle: oracle.jdbc.driver.OracleDriver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student addStudent(String name, int age, int year) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (name, age, year) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setInt(3, year);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating student failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String studentId = generatedKeys.getString(1);
                    return new Student(studentId, name, age, year);
                } else {
                    throw new SQLException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Student updateStudent(Student newStudent) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE students SET name = ?, age = ?, year = ? WHERE student_id = ?")) {

            stmt.setString(1, newStudent.getName());
            stmt.setInt(2, newStudent.getAge());
            stmt.setInt(3, newStudent.getYear());
            stmt.setString(4, newStudent.getStudentId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating student failed, no rows affected.");
            }

            return newStudent;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Student deleteStudent(Student student) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?")) {

            stmt.setString(1, student.getStudentId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting student failed, no rows affected.");
            }

            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public Student findStudentById(String studentId) {
        Student student = null;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?")) {

            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    int year = rs.getInt("year");

                    student = new Student(studentId, name, age, year);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Stream<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int year = rs.getInt("year");

                Student student = new Student(studentId, name, age, year);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students.stream();
    }
}