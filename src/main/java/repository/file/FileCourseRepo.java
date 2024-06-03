package repository.file;

import entities.Course;
import repository.CourseMangement;

import java.util.Collection;

public class FileCourseRepo implements CourseMangement {
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
    public Collection<Course> getAllCourses() {
        return null;
    }
}
