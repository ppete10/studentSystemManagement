package repository;

import entities.Course;
import entities.Enrollment;
import entities.Grade;
import service.GradeCalculation;

import java.util.Collection;

public class MemGradeCalculationRepo implements GradeCalculation {

    @Override
    public double calculateGPA(Collection<Grade> grades, Collection<Enrollment> enrollments) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            Enrollment enrollment = findEnrollmentById(enrollments, grade.getGradeEnrollId());
            if (enrollment != null) {
                for (Course course : enrollment.getCourse()) {
                    totalCredits += course.getCredits();
                    totalPoints += grade.getGrade() * course.getCredits();
                }
            }
        }

        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }

    private Enrollment findEnrollmentById(Collection<Enrollment> enrollments, String enrollId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentEnrollId().equals(enrollId)) {
                return enrollment;
            }
        }
        return null;
    }
}
