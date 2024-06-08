package repository.jdbc;

import entities.Student;
import repository.StudentManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JdbcStudentRepo implements StudentManagement {

    public JdbcStudentRepo() {
        createStudentTable();
    }

    private void createStudentTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Students", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Students ("
                        + "student_Id VARCHAR(10) PRIMARY KEY,"
                        + "name VARCHAR(100) NOT NULL,"
                        + "age INT,"
                        + "\"year\" INT"
                        + ")";
                stmt.executeUpdate(createTableSQL);
            }
        } catch (SQLException e) {

        }
    }

    private String generateStudentId(Connection conn) throws SQLException {
        String prefix = "S66";
        String query = "SELECT student_Id FROM Students ORDER BY student_Id DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String lastId = rs.getString("student_Id");
                if (lastId != null && lastId.startsWith(prefix)) {
                    int lastNumber = Integer.parseInt(lastId.substring(3));
                    return String.format(prefix + "%03d", (lastNumber + 1));
                }
            }
            return String.format(prefix + "%03d", 1);
        }
    }

    @Override
    public Student addStudent(String name, int age, int year) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Students (student_Id, name, age, \"year\") VALUES (?, ?, ?, ?)")) {

            String studentId = generateStudentId(conn);
            stmt.setString(1, studentId);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setInt(4, year);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating student failed.");
            }

            return new Student(studentId, name, age, year);
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Student updateStudent(Student s) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Students SET name=?, age=?, \"year\"=? WHERE student_Id=?")) {

            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getAge());
            stmt.setInt(3, s.getYear());
            stmt.setString(4, s.getStudentId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating student failed.");
            }

            return s;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Student deleteStudent(Student student) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Students WHERE student_Id=?")) {

            stmt.setString(1, student.getStudentId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting student failed.");
            }

            return student;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Student findStudentById(String studentId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Students WHERE student_Id=?")) {

            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    int year = rs.getInt("year");
                    return new Student(studentId, name, age, year);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Stream<Student> getAllStudent() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Students")) {

            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                String studentId = rs.getString("student_Id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int year = rs.getInt("year");
                students.add(new Student(studentId, name, age, year));
            }
            return students.stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }
}