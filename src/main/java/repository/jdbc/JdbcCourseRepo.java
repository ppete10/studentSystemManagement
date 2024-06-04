package repository.jdbc;

import entities.Course;
import repository.CourseMangement;

import java.util.Collection;
import java.util.stream.Stream;

public class JdbcCourseRepo implements CourseMangement {
    @Override
    public Course addCourse(String courseId, String courseName, int credits) {
        return null;
    }

    @Override
    public Course updateCourse(String courseCode, Course c) {
        return null;
    }

    @Override
    public Course deleteCourse(Course course) {
        return null;
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        return null;
    }

    @Override
    public Stream<Course> getAllCourses() {
        return null;
    }
}
