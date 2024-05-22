package entities;

public class Course {
    private final String courseCode;
    private final String courseName;
    private final int credits;

    public Course(String courseCode, String courseName, int credits) {
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
        return String.format("Course: %s, %s, %d", courseCode, courseName, credits);
    }
}
