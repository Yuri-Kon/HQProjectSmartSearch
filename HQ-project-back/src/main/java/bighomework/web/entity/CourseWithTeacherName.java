package bighomework.web.entity;


import lombok.Data;

@Data
public class CourseWithTeacherName {
  private String course_id;
  private String exe_id;
  private Integer teacher_id;
  private String teacher_name; // ✅ 新增
  private String course_name;
  private String course_info;
  private Double course_fee;
  private String course_state;
  private String course_start;
  private String course_end;
  private String course_place;
}
