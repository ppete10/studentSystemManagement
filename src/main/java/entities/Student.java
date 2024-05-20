package entities;

public class Student {
    private final String studentId;
    private String firstName;
    private String lastName;
    private int age;
    private int year;

    public Student(String studentId, String firstName, String lastName, int age, int year) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSemester() {
        return year;
    }

    public void setSemester(int semester) {
        this.year = semester;
    }

    @Override
    public String toString() {
        return String.format("Account(%s,%s,%s,%d,%d)", studentId, firstName, lastName, age, year);
    }
}
