package entities;

import java.util.HashSet;
import java.util.Set;

public class Enrollment {
    private final String studentEnrollId;
    private Set<Course> course;

    public Enrollment(String enrollmentId) {
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

    @Override
    public String toString() {
        return String.format("Enrollment(%s%s)", studentEnrollId, course);
    }

}
