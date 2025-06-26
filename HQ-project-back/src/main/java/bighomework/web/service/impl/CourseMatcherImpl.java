package bighomework.web.service.impl;

import bighomework.web.entity.Course;
import bighomework.web.entity.CourseWithTeacherName;
import bighomework.web.entity.Teacher;
import bighomework.web.mapper.CourseMapper;
import bighomework.web.mapper.TeacherMapper;
import bighomework.web.service.CourseMatcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseMatcherImpl implements CourseMatcher {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Autowired
  private CourseMapper courseMapper;

  @Autowired
  private TeacherMapper teacherMapper;

  @Override
  public List<CourseWithTeacherName> matchCourses(
      String courseName,
      Integer teacherId,
      List<LocalDate> dates,
      String coursePlace,
      String courseInfo,
      String courseId) {
    List<String> dateStrs = dates != null ? dates.stream()
        .map(d -> d.format(FORMATTER))
        .collect(Collectors.toList()) : null;

    List<Course> candidates = courseMapper.queryCandidates(
        courseName, teacherId, dateStrs, coursePlace, courseInfo, courseId);

    return candidates.stream()
            .sorted(Comparator.comparingInt(c ->
                    -score(c, courseName, teacherId, dateStrs, coursePlace, courseInfo, courseId)))
            .map(course -> {
              CourseWithTeacherName dto = new CourseWithTeacherName();
              BeanUtils.copyProperties(course, dto);
              if (course.getTeacher_id() != null) {
                Teacher teacher = teacherMapper.selectById(course.getTeacher_id());
                dto.setTeacher_name(teacher != null ? teacher.getTeacher_name() : null);
              }
              return dto;
            })
            .collect(Collectors.toList());
  }

  private int score(Course c,
      String courseName,
      Integer teacherId,
      List<String> dateStrs,
      String coursePlace,
      String courseInfo,
      String courseId) {
    int score = 0;

    if (courseName != null && c.getCourse_name() != null &&
        c.getCourse_name().contains(courseName)) {
      score += 3;
    }
    if (teacherId != null && Objects.equals(c.getTeacher_id(), teacherId)) {
      score += 1;
    }
    if (dateStrs != null && !dateStrs.isEmpty()) {
      for (String d : dateStrs) {
        if (c.getCourse_start().compareTo(d) <= 0 && c.getCourse_end().compareTo(d) >= 0) {
          score += 2;
          break;
        }
      }
    }
    if (coursePlace != null && c.getCourse_place() != null &&
        c.getCourse_place().contains(coursePlace)) {
      score += 2;
    }
    if (courseInfo != null && c.getCourse_info() != null &&
        c.getCourse_info().contains(courseInfo)) {
      score += 1;
    }
    if (courseId != null && c.getCourse_id() != null &&
        c.getCourse_id().equals(courseId)) {
      score += 1;
    }

    return score;
  }
}
