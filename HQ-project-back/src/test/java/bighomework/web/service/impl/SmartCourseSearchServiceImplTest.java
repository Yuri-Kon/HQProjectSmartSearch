package bighomework.web.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import bighomework.web.entity.CourseWithTeacherName;
import bighomework.web.entity.QueryResult;
import bighomework.web.service.CourseMatcher;
import bighomework.web.service.QueryParser;
import bighomework.web.service.TeacherResolver;

public class SmartCourseSearchServiceImplTest {

  @Mock
  private QueryParser queryParser;

  @Mock
  private TeacherResolver teacherResolver;

  @Mock
  private CourseMatcher courseMatcher;

  @InjectMocks
  private SmartCourseSearchServiceImpl service;

  private QueryResult buildResult(String name, String teacher, String place, String time, String info, String id) {
    QueryResult r = new QueryResult();
    if (name != null) r.setCourse_name(name);
    if (teacher != null) r.setTeacher_name(teacher);
    if (place != null) r.setCourse_place(place);
    if (time != null) r.setCourse_time_expr(time);
    if (info != null) r.setCourse_info(info);
    if (id != null) r.setCourse_id(id);
    return r;
  }

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void shouldReturnCoursesWhenAllFieldsAreValid() throws Exception {
    QueryResult result = buildResult("英语", "张老师", "正心楼", "周三", "语言", "");

    given(queryParser.parser("找英语课")).willReturn(result);
    given(teacherResolver.resolveTeacherId("张老师")).willReturn(1001);
    given(courseMatcher.matchCourses(
            eq("英语"), eq(1001),
            anyList(),
            eq("正心楼"), eq("语言"), isNull()))
            .willReturn(List.of(new CourseWithTeacherName()));

    List<CourseWithTeacherName> output = service.searchCoursesByQuery("找英语课");

    assertEquals(1, output.size());
  }

  @Test
  void shouldHandleEmptyTeacherAndTime() throws Exception {
    QueryResult result = buildResult("AI", "", "", "", "", "");

    given(queryParser.parser("AI")).willReturn(result);
    given(courseMatcher.matchCourses("AI", null, List.of(), null, null, null))
            .willReturn(List.of(new CourseWithTeacherName()));

    List<CourseWithTeacherName> output = service.searchCoursesByQuery("AI");

    assertEquals(1, output.size());
  }

  @Test
  void shouldThrowExceptionWhenQueryParserFails() throws Exception {
    given(queryParser.parser("错误")).willThrow(new RuntimeException("Dify故障"));

    RuntimeException ex = assertThrows(RuntimeException.class,
            () -> service.searchCoursesByQuery("错误"));

    assertTrue(ex.getMessage().contains("解析自然语言失败"));
  }

  @Test
  void shouldWorkWhenAllFieldsEmpty() throws Exception {
    QueryResult result = buildResult("", "", "", "", "", "");

    given(queryParser.parser("啥都没有")).willReturn(result);
    given(courseMatcher.matchCourses(null, null, List.of(), null, null, null))
            .willReturn(List.of(new CourseWithTeacherName(), new CourseWithTeacherName()));

    List<CourseWithTeacherName> output = service.searchCoursesByQuery("啥都没有");

    assertEquals(2, output.size());
  }
}
