package bighomework.integration;

import bighomework.web.entity.Course;
import bighomework.web.entity.CourseWithTeacherName;
import bighomework.web.entity.QueryResult;
import bighomework.web.service.CourseMatcher;
import bighomework.web.service.QueryParser;
import bighomework.web.service.TeacherResolver;
import bighomework.web.service.impl.SmartCourseSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

@SpringBootTest(classes = bighomework.web.WebApplication.class)
class SmartSearchIntegrationTest {

  @Autowired
  private SmartCourseSearchServiceImpl service;

  @MockBean
  private QueryParser queryParser;

  @MockBean
  private TeacherResolver teacherResolver;

  @MockBean
  private CourseMatcher courseMatcher;

  @Test
  void fullSearchFlow_shouldReturnSortedCourses() throws Exception {
    // 1. 构造 Dify 解析结果
    QueryResult qr = new QueryResult();
    qr.setCourse_name("英语");
    qr.setTeacher_name("张老师");
    qr.setCourse_place("正心楼");
    qr.setCourse_time_expr("周三");

    BDDMockito.given(queryParser.parser("找英语课")).willReturn(qr);
    BDDMockito.given(teacherResolver.resolveTeacherId("张老师")).willReturn(1001);

    // 2. 模拟 courseMapper 返回 2 个候选课程
    CourseWithTeacherName c1 = new CourseWithTeacherName();
    c1.setCourse_id("C1");
    c1.setCourse_name("英语");
    c1.setTeacher_id(1001);
    c1.setCourse_start("2025-06-01");
    c1.setCourse_end("2025-06-30");
    c1.setCourse_place("正心楼");

    CourseWithTeacherName c2 = new CourseWithTeacherName();
    c2.setCourse_id("C2");
    c2.setCourse_name("数学");
    c2.setTeacher_id(1002);
    c2.setCourse_start("2025-06-01");
    c2.setCourse_end("2025-06-30");
    c2.setCourse_place("教学楼");

    BDDMockito.given(courseMatcher.matchCourses(
        eq("英语"), eq(1001), anyList(),
        eq("正心楼"), isNull(), isNull())).willReturn(List.of(c1, c2));

    // 3. 运行主流程
    List<CourseWithTeacherName> result = service.searchCoursesByQuery("找英语课");

    // 4. 验证
    assertEquals(2, result.size());
    assertEquals("C1", result.get(0).getCourse_id()); // 应为高匹配项
  }
}
