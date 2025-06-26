package bighomework.web.service;

import bighomework.web.entity.Course;
import bighomework.web.entity.CourseWithTeacherName;

import java.util.List;

public interface SmartCourseSearchService {
  List<CourseWithTeacherName> searchCoursesByQuery(String queryText) throws Exception;
}
