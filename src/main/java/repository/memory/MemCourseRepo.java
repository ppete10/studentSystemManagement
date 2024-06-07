package repository.memory;

import entities.Course;
import repository.CourseMangement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemCourseRepo implements CourseMangement {
    private Map<String, Course> courseRepo = new HashMap<>();
    @Override
    public Course addCourse(String courseId, String courseName, int credits) {
        Course c = new Course(courseId, courseName, credits);
            courseRepo.put(c.getCourseCode(), c);
        return c;
    }

    @Override
    public Course updateCourse(String courseCode, Course c) {
        try {
            courseRepo.replace(courseCode, c);
        } catch (Exception e) {
            return null;
        }
        return c;
    }

    @Override
    public Course deleteCourse(Course course) {
        try {
            courseRepo.remove(course.getCourseCode(), course);
        } catch (Exception e) {
            return null;
        }
        return course;
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        return courseRepo.get(courseCode);
    }

    @Override
    public Stream<Course> getAllCourses() {
        return courseRepo.values().stream();
    }
}
