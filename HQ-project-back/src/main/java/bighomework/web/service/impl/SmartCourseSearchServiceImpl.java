package bighomework.web.service.impl;

import bighomework.web.entity.CourseWithTeacherName;
import bighomework.web.entity.QueryResult;
import bighomework.web.service.*;
import bighomework.web.util.TimeExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class SmartCourseSearchServiceImpl implements SmartCourseSearchService {

  @Autowired
  private QueryParser queryParser;

  @Autowired
  private CourseMatcher courseMatcher;

  @Autowired
  private TeacherResolver teacherResolver;

  @Override
  public List<CourseWithTeacherName> searchCoursesByQuery(String queryText) {
    System.out.println("🧠 查询原始输入：" + queryText);
    queryText = queryText.trim();
    System.out.println("✂️ 清洗后 query：" + queryText);

    QueryResult parsed;

    try {
      parsed = queryParser.parser(queryText);
      System.out.println("📦 Dify 返回：" + parsed);
    } catch (Exception e) {
      throw new RuntimeException("解析自然语言失败：" + e.getMessage(), e);
    }

    String courseName = emptyToNull(parsed.getCourse_name());
    String courseInfo = emptyToNull(parsed.getCourse_info());
    String coursePlace = emptyToNull(parsed.getCourse_place());
    String courseId = emptyToNull(parsed.getCourse_id());

    Integer teacherId = null;

    if (courseName != null) {
      if (courseName.endsWith("课")) {
        courseName = courseName.substring(0, courseName.length() - 1); // 去除“课”
      }
      System.out.println("📘 课程名: " + courseName);
    }

    if (parsed.getTeacher_name() != null && !parsed.getTeacher_name().isBlank()) {
      teacherId = teacherResolver.resolveTeacherId(parsed.getTeacher_name());
      System.out.println("👤 匹配教师ID：" + teacherId);
    }

    List<LocalDate> dates = Collections.emptyList();
    if (parsed.getCourse_time_expr() != null && !parsed.getCourse_time_expr().isBlank()) {
      dates = TimeExpressionParser.parseCourseTimeExpr(parsed.getCourse_time_expr());
      System.out.println("📅 匹配到时间点：" + dates);
    }

    List<CourseWithTeacherName> result = courseMatcher.matchCourses(
            courseName, teacherId, dates, coursePlace, courseInfo, courseId
    );

    System.out.println("📊 匹配课程数: " + result.size());
    for (CourseWithTeacherName c : result) {
      System.out.println("  - 课程: " + c.getCourse_id() + " / " + c.getCourse_name() + " / 教师: " + c.getTeacher_name());
    }

    return result;
  }

  private String emptyToNull(String s) {
    return (s == null || s.isBlank()) ? null : s;
  }
}
