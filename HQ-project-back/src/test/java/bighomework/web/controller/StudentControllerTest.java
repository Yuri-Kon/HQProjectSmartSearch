package bighomework.web.controller;

import bighomework.web.entity.CourseWithTeacherName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import bighomework.web.entity.Course;
import bighomework.web.service.SmartCourseSearchService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SmartCourseSearchService smartCourseSearchService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldReturnCoursesWhenQueryIsValid() throws Exception {
    CourseWithTeacherName courseWithTeacherName = new CourseWithTeacherName();
    courseWithTeacherName.setCourse_id("ENG001");
    courseWithTeacherName.setCourse_name("英语课");

    BDDMockito.given(smartCourseSearchService.searchCoursesByQuery("英语"))
        .willReturn(Collections.singletonList(courseWithTeacherName));

    mockMvc.perform(post("/student/searchSmartCourse")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("query", "英语"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].course_id").value("ENG001"))
        .andExpect(jsonPath("$[0].course_name").value("英语课"));
  }

  @Test
  void shouldReturnEmptyListWhenQueryIsMissing() throws Exception {
    mockMvc.perform(post("/student/searchSmartCourse")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of())))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  void shouldReturnEmptyListWhenServiceReturnsNothing() throws Exception {
    BDDMockito.given(smartCourseSearchService.searchCoursesByQuery(anyString()))
        .willReturn(Collections.emptyList());

    mockMvc.perform(post("/student/searchSmartCourse")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("query", "不存在"))))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }
}
