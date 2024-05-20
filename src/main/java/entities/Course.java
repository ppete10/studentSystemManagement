package entities;

public enum Course {
    INT103("INT103","Advanced Programming", 3),
    INT107("INT107","Computing Platform", 3),
    INT104("INT104", "User Experience Design", 1);

    private final String courseCode;
    private final String courseName;
    private final int credits;

    Course(String courseCode, String courseName, int credits) {
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
}
