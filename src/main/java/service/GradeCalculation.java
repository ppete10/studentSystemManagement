package service;

import entities.Enrollment;
import entities.Grade;

import java.util.Collection;

public interface GradeCalculation {
    double calculateGPA(Collection<Grade> grades, Collection<Enrollment> enrollments);
}
