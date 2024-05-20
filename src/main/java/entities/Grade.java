package entities;

public class Grade {
    private String gradeEnrollId;
    private double grade;

    public Grade(String gradeEnrollId, double grade) {
        this.gradeEnrollId = gradeEnrollId;
        this.grade = grade;
    }

    public String getGradeEnrollId() {
        return gradeEnrollId;
    }

    public void setGradeEnrollId(String gradeEnrollId) {
        this.gradeEnrollId = gradeEnrollId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("Grade(%s is %.2f)", gradeEnrollId, grade);
    }
}
