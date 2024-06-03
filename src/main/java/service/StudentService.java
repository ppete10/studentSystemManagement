package service;


import entities.Course;
import entities.Enrollment;
import entities.Student;
import repository.CourseMangement;
import repository.EnrollManagement;
import repository.StudentManagement;

import java.util.Collection;

public class StudentService {
    private StudentManagement studentRepo;
    private EnrollManagement enrollRepo;
    private final CourseMangement courseRepo;

    public StudentService(StudentManagement student, EnrollManagement enroll, CourseMangement course) {
        this.studentRepo = student;
        this.enrollRepo = enroll;
        this.courseRepo = course;
    }

    // managing student
    public Student registerStudent(String firstName, String lastName, int age, int year) {
        if (firstName == null || lastName == null || age <= 0 || year <= 0)
            return null;
        return studentRepo.addStudent(firstName, lastName, age, year);
    }

    public Student reStudent(String studentId, String firstName, String lastName, int age, int year) {
        Student s = studentRepo.findStudentById(studentId);
        if (firstName == null || lastName == null || age <= 0 || year <= 0 || s == null)
            return null;
        Student s2 = new Student(s.getStudentId(),firstName, lastName, age, year);
        return studentRepo.updateStudent(studentId, s2);
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

    public Collection<Student> getAllStudent() {
        return studentRepo.getAllStudent();
    }


    // managing enrollment
    public Enrollment enrollStudentInCourse(String studentId, Course... course) {
        if (studentId == null || findStudentById(studentId) == null)
            return null;
        return enrollRepo.addEnrollment(studentId, course);
    }

    public Enrollment changeEnrollment(String studentEnrollId, Course... course) {
        Enrollment e = findEnrollmentByStudentId(studentEnrollId);
        if (studentEnrollId == null || e == null)
            return null;
        return enrollRepo.updateEnrollment(e, course);
    }

    public Enrollment deleteEnrollment (String enrollId){
        Enrollment e = enrollRepo.getEnrollmentByStudentId(enrollId);
        if (enrollId == null || e == null)
            return null;
        return enrollRepo.deleteEnrollment(e);
    }

    public Enrollment findEnrollmentByStudentId(String studentEnrollId) {
        Enrollment e = enrollRepo.getEnrollmentByStudentId(studentEnrollId);
        if (studentEnrollId == null || e == null)
            return null;
        return enrollRepo.getEnrollmentByStudentId(studentEnrollId);
    }

    public Collection<Enrollment> getAllEnrollment() {
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
    public Course deleteCourse(String courseCode){
        Course c = courseRepo.getCourseByCode(courseCode);
        if (courseCode == null || c == null)
            return null;
        return courseRepo.deleteCourse(c);
    }


    public Course getCourseByCode(String courseCode){
        Course c = getCourseByCode(courseCode);
        if (courseCode == null || c == null)
            return null;
        return courseRepo.getCourseByCode(courseCode);
    }

    public Collection<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }
}
