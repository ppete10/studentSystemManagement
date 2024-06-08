package entities;

import exception.InvalidCourseFormatException;

import java.io.Serializable;

public class Course implements Serializable {
    private final String courseCode;
    private final String courseName;
    private final int credits;

    public Course(String courseCode, String courseName, int credits) {
        if (courseCode == null || courseCode.isBlank() ||
                courseName == null || courseName.isBlank() || credits <= 0)
            throw new InvalidCourseFormatException();
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", courseCode, courseName, credits);
    }

    public String toStringFormat() {
        return String.format("%-7s %-30s %-3d", courseCode, courseName, credits);
    }
}
