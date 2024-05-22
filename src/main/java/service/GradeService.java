/*
package service;

import entities.Enrollment;
import entities.Grade;
import repository.MemEnrollmentRepo;
import repository.MemGradeCalculationRepo;

import java.util.List;

public class GradeService {
    private final MemGradeCalculationRepo gradeRepository;
    private final MemEnrollmentRepo enrollmentRepository;

    public GradeService(MemGradeCalculationRepo gradeRepository, MemEnrollmentRepo enrollmentRepository) {
        this.gradeRepository = gradeRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public boolean addGrade(String studentId, String courseCode, double grade) {
        // ตรวจสอบว่านักเรียนได้ลงทะเบียนในวิชานี้หรือไม่
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentByStudentId(studentId);
        boolean isEnrolled = enrollments.stream()
                .anyMatch(enrollment -> enrollment.getCourse().equals(courseCode));

        if (isEnrolled) {
            Grade newGrade = new Grade(studentId, courseCode, grade);
            gradeRepository.addGrade(newGrade);
            return true;
        } else {
            return false; // นักเรียนไม่ได้ลงทะเบียนในวิชานี้
        }
    }

    public List<Grade> getGradesByStudentId(String studentId) {
        return gradeRepository.getGradesByStudentId(studentId);
    }

    public List<Grade> getGradesByCourseCode(String courseCode) {
        return gradeRepository.getGradesByCourseCode(courseCode);
    }

    public double calculateGPA(String studentId) {
        List<Grade> grades = gradeRepository.getGradesByStudentId(studentId);
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentsByStudentId(studentId);

        if (grades.isEmpty() || enrollments.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            for (Enrollment enrollment : enrollments) {
                if (grade.getCourseCode().equals(enrollment.getCourseCode())) {
                    totalPoints += grade.getGrade() * enrollment.getCredits();
                    totalCredits += enrollment.getCredits();
                    break;
                }
            }
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
}

*/
