package service;


import entities.Course;
import entities.Enrollment;
import entities.Grade;
import entities.Student;

import java.util.Collection;

public class StudentService {
    private StudentManagement students;
    private EnrollManagement enrolls;
    private GradeCalculation gradeCal;

    public StudentService(StudentManagement student, EnrollManagement enroll) {
        this.students = student;
        this.enrolls = enroll;
    }

    public Student registerStudent(String firstName, String lastName, int age, int year) {
        if (firstName == null || lastName == null || age <= 0 || year <= 0)
            return null;
        return students.addStudent(firstName, lastName, age, year);
    }

    public Student reStudent(String studentId, String firstName, String lastName, int age, int year) {
        Student s = students.findStudentById(studentId);
        if (firstName == null || lastName == null || age <= 0 || year <= 0 || s == null)
            return null;
        return students.updateStudent(s);
    }

    public Student deleteStudent(String studentId) {
        Student s = students.findStudentById(studentId);
        if (studentId == null || s == null)
            return null;
        return students.deleteStudent(s);
    }

    public Student findStudentById(String studentId) {
        if (studentId == null)
            return null;
        return students.findStudentById(studentId);
    }

    public Collection<Student> getAllStudent() {
        return students.getAllStudent();
    }

    public Enrollment enrollment(String studentId, Course... course) {
        if (studentId == null || findStudentById(studentId) == null)
            return null;

        return enrolls.addEnrollment(studentId, course);
    }

    public Enrollment changeEnrollment(String enrollId, Course... course) {
        Enrollment e = findEnrollmentByStudentId(enrollId);
        if (enrollId == null || e == null)
            return null;
        for (Course c : course){
            e.addCourse(c);
        }
        return enrolls.updateEnrollment(e);
    }


    public Enrollment findEnrollmentByStudentId(String studentEnrollId) {
        return enrolls.getEnrollmentByStudentId(studentEnrollId);
    }

    public Enrollment deleteEnrollment(String enrollId) {
        Enrollment e = enrolls.getEnrollmentByStudentId(enrollId);
        if (enrollId == null || e == null)
            return null;
        return enrolls.deleteEnrollment(e);
    }

    public Collection<Enrollment> getAllEnrollment() {
        return enrolls.getAllEnrollment();
    }

    public double calculateGPA(Collection<Grade> grades, Collection<Enrollment> enrollments) {
        return gradeCal.calculateGPA(grades, enrollments);
    }

    public void setGradeCalculation(GradeCalculation gradeCal) {
        this.gradeCal = gradeCal;
    }
}
