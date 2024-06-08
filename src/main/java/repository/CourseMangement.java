package repository;

import entities.Course;

import java.util.stream.Stream;

public interface CourseMangement {
    Course addCourse(String courseId, String courseName, int credits);

    Course updateCourse(String courseCode, Course c);

    Course deleteCourse(Course course);

    Course getCourseByCode(String courseCode);

    Stream<Course> getAllCourses();
}
