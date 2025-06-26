package bighomework.web.service.impl;

import bighomework.web.mapper.TeacherMapper;
import bighomework.web.service.TeacherResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherResolverImplTest {

  @Mock
  private TeacherMapper teacherMapper;

  @InjectMocks
  private TeacherResolverImpl teacherResolver;

  @BeforeEach
  void setUp() {
    openMocks(this); // 初始化Mock对象
  }

  @Test
  void shouldReturnTeacherIdWhenExactNameGiven() {
    given(teacherMapper.findTeacherIdByFuzzyName("张三")).willReturn(1001);

    Integer result = teacherResolver.resolveTeacherId("张三");
    assertEquals(1001, result);
  }

  @Test
  void shouldReturnTeacherIdWhenTeacherSuffixGiven() {
    given(teacherMapper.findTeacherIdByFuzzyName("张三")).willReturn(1001);

    Integer result = teacherResolver.resolveTeacherId("张三老师");
    assertEquals(1001, result);
  }

  @Test
  void shouldReturnNullWhenNameIsEmpty() {
    Integer result = teacherResolver.resolveTeacherId("");
    assertNull(result);
  }

  @Test
  void shouldReturnNullWhenNoMatchFound() {
    given(teacherMapper.findTeacherIdByFuzzyName("不存在")).willReturn(null);

    Integer result = teacherResolver.resolveTeacherId("不存在老师");
    assertNull(result);
  }
}
