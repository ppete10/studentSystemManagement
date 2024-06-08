package service;


import entities.Course;
import entities.Enrollment;
import entities.Student;
import repository.CourseMangement;
import repository.EnrollManagement;
import repository.StudentManagement;

import java.util.Set;
import java.util.stream.Stream;

public class SystemService {
    private final StudentManagement studentRepo;
    private final EnrollManagement enrollRepo;
    private final CourseMangement courseRepo;

    public SystemService(StudentManagement student, EnrollManagement enroll, CourseMangement course) {
        this.studentRepo = student;
        this.enrollRepo = enroll;
        this.courseRepo = course;
    }

    // managing student
    public Student registerStudent(String name, int age, int year) {
        if (name == null || age <= 0 || year <= 0)
            return null;
        return studentRepo.addStudent(name, age, year);
    }

    public Student reStudent(String studentId, String name, int age, int year) {
        Student s = studentRepo.findStudentById(studentId);
        if (name == null || age <= 0 || year <= 0 || s == null)
            return null;
        Student s2 = new Student(s.getStudentId(), name, age, year);
        return studentRepo.updateStudent(s2);
    }

    public Student deleteStudent(String studentId) {
        Student s = studentRepo.findStudentById(studentId);
        if (studentId == null || s == null)
            return null;
        return studentRepo.deleteStudent(s);
    }

    public Student findStudentById(String studentId) {
        if (studentId == null)
            return null;
        return studentRepo.findStudentById(studentId);
    }

    public Stream<Student> getAllStudent() {
        return studentRepo.getAllStudent();
    }


    // managing enrollment
    public Enrollment enrollStudentInCourse(String studentId, Set<Course> course) {
        if (studentId == null || findStudentById(studentId) == null)
            return null;
        return enrollRepo.addEnrollment(studentId, course);
    }

    public Enrollment changeEnrollment(String studentEnrollId, Set<Course> course) {
        Enrollment e = findEnrollmentByStudentId(studentEnrollId);
        if (studentEnrollId == null || e == null)
            return null;
        return enrollRepo.updateEnrollment(e, course);
    }

    public Enrollment deleteEnrollment(String studentEnrollId) {
        Enrollment e = enrollRepo.getEnrollmentByStudentId(studentEnrollId);
        if (studentEnrollId == null || e == null)
            return null;
        return enrollRepo.deleteEnrollment(e);
    }

    public Enrollment findEnrollmentByStudentId(String studentEnrollId) {
        Enrollment e = enrollRepo.getEnrollmentByStudentId(studentEnrollId);
        if (studentEnrollId == null || e == null)
            return null;
        return enrollRepo.getEnrollmentByStudentId(studentEnrollId);
    }

    public Stream<Enrollment> getAllEnrollment() {
        return enrollRepo.getAllEnrollment();
    }

    //managing course
    public Course addCourse(String courseCode, String courseName, int credits) {
        if (courseCode == null || courseName == null || credits <= 0)
            return null;
        return courseRepo.addCourse(courseCode, courseName, credits);
    }

    public Course updateCourse(String courseCode, String courseName, int credits) {
        Course c = getCourseByCode(courseCode);
        if (courseCode == null || c == null || courseCode == null || courseName == null || credits <= 0)
            return null;
        Course c2 = new Course(courseCode, courseName, credits);
        return courseRepo.updateCourse(courseCode, c2);
    }

    public Course deleteCourse(String courseCode) {
        Course c = courseRepo.getCourseByCode(courseCode);
        if (courseCode == null || c == null)
            return null;
        return courseRepo.deleteCourse(c);
    }


    public Course getCourseByCode(String courseCode) {
        if (courseCode == null)
            return null;
        return courseRepo.getCourseByCode(courseCode);
    }

    public Stream<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }
}
