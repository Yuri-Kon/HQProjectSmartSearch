package bighomework.web.service;

import java.time.LocalDate;
import bighomework.web.entity.Course;
import bighomework.web.entity.CourseWithTeacherName;

import java.util.List;

public interface CourseMatcher {
  List<CourseWithTeacherName> matchCourses(
          String courseName,
          Integer teacherId,
          List<LocalDate> dates,
          String coursePlace,
          String courseInfo,
          String courseId);
}
