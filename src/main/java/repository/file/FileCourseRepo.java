package repository.file;

import entities.Course;
import repository.CourseMangement;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static repository.file.FileStudentRepo.PATH;

public class FileCourseRepo implements CourseMangement {
    private String filename = PATH + "course.dat";
    private Map<String, Course> courseRepo;

    public FileCourseRepo() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fi = new FileInputStream(file);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {

                courseRepo = (TreeMap<String, Course>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {

            }
        } else {
            courseRepo = new TreeMap<>();
        }
    }

    private void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(courseRepo);
            oos.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public Course addCourse(String courseId, String courseName, int credits) {
        Course c = new Course(courseId, courseName, credits);
        courseRepo.put(c.getCourseCode(), c);
        writeToFile();
        return c;
    }

    @Override
    public Course updateCourse(String courseCode, Course c) {
        try {
            courseRepo.replace(courseCode, c);
        } catch (Exception e) {
            return null;
        }
        writeToFile();
        return c;
    }

    @Override
    public Course deleteCourse(Course course) {
        try {
            courseRepo.remove(course.getCourseCode(), course);
        } catch (Exception e) {
            return null;
        }
        writeToFile();
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
