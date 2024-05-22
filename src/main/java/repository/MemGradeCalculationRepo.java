/*
package repository;

import entities.Course;
import entities.Enrollment;
import entities.Grade;
import service.GradeCalculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemGradeCalculationRepo implements GradeCalculation {
    private List<Grade> grades = new ArrayList<>();

    public double addGrade(Grade grade) {
        grades.add(grade);
        return ;
    }
    public List<Grade> getGradesByStudentId(String studentId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudentId().equals(studentId)) {
                result.add(g);
            }
        }
        return result;
    }
}
*/
