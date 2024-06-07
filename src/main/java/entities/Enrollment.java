package entities;


import exception.InvalidEnrollmentFormatException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Enrollment implements Serializable {
    private final String studentEnrollId;
    private Set<Course> course;

    public Enrollment(String enrollmentId) {
        if (enrollmentId == null || enrollmentId.isBlank())
            throw new InvalidEnrollmentFormatException();

        this.studentEnrollId = enrollmentId;
        this.course = new HashSet<>();
    }

    public String getStudentEnrollId() {
        return studentEnrollId;
    }

    public Set<Course> getCourse() {
        return course;
    }

    public void addCourse(Course course) {
        this.course.add(course);
    }

    public void updateCourse(Set<Course> course) {
        this.course = course;
    }

    @Override
    public String toString() {
        StringBuilder courseList = new StringBuilder();
        if (course.isEmpty()) {
            courseList.append("\n       [No courses enrolled]");
        } else {
            for (Course c : course) {
                courseList.append("\n       ");
                courseList.append(c.toStringFormat());
            }
        }
        return String.format("%-5s%s", studentEnrollId, courseList);
    }
}
