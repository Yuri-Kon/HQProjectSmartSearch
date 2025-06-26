package bighomework.web.service.impl;

import bighomework.web.entity.Course;
import bighomework.web.entity.CourseWithTeacherName;
import bighomework.web.entity.Teacher;
import bighomework.web.mapper.CourseMapper;
import bighomework.web.mapper.TeacherMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

public class CourseMatcherImplTest {

  @Mock
  private CourseMapper courseMapper;

  @Mock
  private TeacherMapper teacherMapper;

  @InjectMocks
  private CourseMatcherImpl courseMatcher;

  private Course createCourse(String id, String name, String place, String info, String start, String end, Integer teacherId) {
    Course c = new Course();
    c.setCourse_id(id);
    c.setCourse_name(name);
    c.setCourse_place(place);
    c.setCourse_info(info);
    c.setCourse_start(start);
    c.setCourse_end(end);
    c.setTeacher_id(teacherId);
    return c;
  }

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void shouldReturnSortedByMatchScore() {
    Course c1 = createCourse("C1", "英语课", "正心楼", "听说读写", "2025-06-01", "2025-06-30", 1001);
    Course c2 = createCourse("C2", "英语", "教学楼", "语言", "2025-06-01", "2025-06-15", 1002);
    Course c3 = createCourse("C3", "数学", "正心楼", "数学基础", "2025-06-01", "2025-06-30", 1001);

    given(courseMapper.queryCandidates("英语课", 1001, List.of("2025-06-12"), "正心楼", "听说", null))
            .willReturn(Arrays.asList(c3, c2, c1));

    given(teacherMapper.selectById(1001)).willReturn(mockTeacher(1001, "张老师"));
    given(teacherMapper.selectById(1002)).willReturn(mockTeacher(1002, "李老师"));

    List<CourseWithTeacherName> result = courseMatcher.matchCourses(
            "英语课", 1001, List.of(LocalDate.of(2025, 6, 12)), "正心楼", "听说", null);

    assertEquals(3, result.size());
    assertEquals("C1", result.get(0).getCourse_id());
    assertEquals("张老师", result.get(0).getTeacher_name());
  }

  @Test
  void shouldWorkWithSingleField() {
    Course c = createCourse("CX", "人工智能", "", "", "2025-06-01", "2025-07-01", null);
    given(courseMapper.queryCandidates("人工智能", null, null, null, null, null))
            .willReturn(List.of(c));

    List<CourseWithTeacherName> result = courseMatcher.matchCourses("人工智能", null, null, null, null, null);

    assertEquals(1, result.size());
    assertEquals("CX", result.get(0).getCourse_id());
    assertNull(result.get(0).getTeacher_name()); // 因为 teacher_id 是 null
  }

  @Test
  void shouldReturnEmptyWhenNoCandidates() {
    given(courseMapper.queryCandidates("不存在", null, null, null, null, null))
            .willReturn(Collections.emptyList());

    List<CourseWithTeacherName> result = courseMatcher.matchCourses("不存在", null, null, null, null, null);

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldHandleNullInputsGracefully() {
    Course c = createCourse("C9", "任何课程", "任意", "无", "2025-01-01", "2025-12-31", null);
    given(courseMapper.queryCandidates(null, null, null, null, null, null))
            .willReturn(List.of(c));

    List<CourseWithTeacherName> result = courseMatcher.matchCourses(null, null, null, null, null, null);

    assertEquals(1, result.size());
    assertEquals("C9", result.get(0).getCourse_id());
  }

  private Teacher mockTeacher(Integer id, String name) {
    Teacher t = new Teacher();
    t.setTeacher_id(id);
    t.setTeacher_name(name);
    return t;
  }
}
