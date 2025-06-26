package bighomework.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bighomework.web.mapper.TeacherMapper;
import bighomework.web.service.TeacherResolver;

@Service
public class TeacherResolverImpl implements TeacherResolver {
  @Autowired
  private TeacherMapper teacherMapper;

  public void setTeacherMapper(TeacherMapper teacherMapper) {
    this.teacherMapper = teacherMapper;
  }

  @Override
  public Integer resolveTeacherId(String teacherName) {
    if (teacherName == null || teacherName.trim().isEmpty()) {
      return null;
    }

    String nameCore = teacherName.replaceAll("(老师|讲师|教师)$", "").trim();
    return teacherMapper.findTeacherIdByFuzzyName(nameCore);
  }

}
