package bighomework.web.mapper;

import bighomework.web.entity.Course;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CourseMapper {

  // 返回一个老师身上有的全部课程
  @Select("select * from course where teacher_id=#{teacher_id}")
  List<Course> SelectByteacher_id(int teacher_id);

  // 返回一个老师所负责的课程的所有状态，目的是查看有多少门处在“未开课”和“已结束”状态
  @Select("select course_state from course where teacher_id=#{teacher_id}")
  List<String> allState(int teacher_id);

  // 我们需要删除课程，根据课程的编号来
  @Delete("delete from course where course_id=#{course_id}")
  void DeleteUnSign(int course_id);

  // 我们需要把离职老师的课程信息进行修改
  @Update("update course set teacher_id=#{teacher_id} where course_id=#{course_id}")
  void UpdateOvered(int teacher_id, String course_id);

  // 按照一个课程的编号来返回一个课程的状态
  @Select("select course_state from course where course_id=#{course_id}")
  List<String> SelectByCourse_id(String course_id);

  @Select("select course_state from course where course_id=#{course_id}")
  String StateByCourse_id(String course_id);

  // 根据一个课程的编号返回相应的课程
  @Select("select * from course where course_id=#{course_id}")
  Course CourseById(String course_id);

  // 返回所有的课程
  @Select("select * from course")
  List<Course> allCourses();

  // 根据执行人的id返回所有他所管理的课程
  @Select("select * from course where exe_id=#{exe_id}")
  List<Course> SelectByExe_id(String exe_id);

  @Select("select count(*) from course where course_id=#{course_id}")
  int countById(String course_id);

  // 执行人增加课程
  @Insert("insert into course values(#{course_id},#{exe_id},#{teacher_id},#{course_name},#{course_info},#{course_fee},"
      +
      "#{course_state},#{course_start},#{course_end},#{course_place})")
  void addCourse(String course_id, String exe_id, int teacher_id, String course_name, String course_info,
      double course_fee,
      String course_state, String course_start, String course_end, String course_place);

  // 执行人删除课程
  @Delete("delete from course where course_id=#{course_id}")
  void deleteCourse(String course_id);

  // 查找所有课的数量
  @Select("select count(*) from course")
  int course_num();

  // 执行人修改课程信息
  @Update("update course set teacher_id=#{teacher_id},course_name=#{course_name},course_info=#{course_info},course_fee=#{course_fee},"
      +
      "course_state=#{course_state},course_start=#{course_start},course_end=#{course_end},course_place=#{course_place} where "
      +
      "course_id=#{course_id}")
  void ModifyCourse(String course_id, int teacher_id, String course_name, String course_info, double course_fee,
      String course_state, String course_start, String course_end, String course_place);

  @Select("select course_name from course where course_id=#{course_id}")
  int NameByCourseId(String course_id);

  @Select({
      "<script>",
      "SELECT * FROM course",
      "WHERE 1=1",
      "<if test='courseName != null and courseName != \"\"'>",
      "AND course_name LIKE CONCAT('%', #{courseName}, '%')",
      "</if>",
      "<if test='teacherId != null'>",
      "AND teacher_id = #{teacherId}",
      "</if>",
      "<if test='dates != null and dates.size > 0'>",
      "AND (",
      "<foreach collection='dates' item='d' separator=' OR '>",
      "(course_start &lt;= #{d} AND course_end &gt;= #{d})",
      "</foreach>",
      ")",
      "</if>",
      "<if test='coursePlace != null and coursePlace != \"\"'>",
      "AND course_place LIKE CONCAT('%', #{coursePlace}, '%')",
      "</if>",
      "<if test='courseInfo != null and courseInfo != \"\"'>",
      "AND course_info LIKE CONCAT('%', #{courseInfo}, '%')",
      "</if>",
      "<if test='courseId != null and courseId != \"\"'>",
      "AND course_id = #{courseId}",
      "</if>",
      "</script>"
  })
  List<Course> queryCandidates(
      @Param("courseName") String courseName,
      @Param("teacherId") Integer teacherId,
      @Param("dates") List<String> dates,
      @Param("coursePlace") String coursePlace,
      @Param("courseInfo") String courseInfo,
      @Param("courseId") String courseId);

}
