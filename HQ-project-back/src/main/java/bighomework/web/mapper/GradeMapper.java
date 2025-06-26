package bighomework.web.mapper;

import bighomework.web.entity.Course;
import bighomework.web.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// 查询的逻辑有两种
@Mapper
public interface GradeMapper {
    // 第一种是只通过course_id查询成绩，适用于讲师和公司的查询
    @Select("select * from grade where course_id=#{course_id}")
    List<Grade> SelectByCourse_id(String course_id);

    // 第二种是通过course_id和stu_id查询成绩，适用于学员的查询 (其实讲师和公司也可以，但这里就不搞太复杂了)
    @Select("select * from grade where course_id=#{course_id} and stu_id=#{stu_id}")
    Grade SelectByCS_id(String course_id,int stu_id);

    @Update("update grade set stu_score=#{stu_score},teacher_evaluate=#{teacher_evaluate} ,where stu_id=#{stu_id} and course_id=#{course_id}")
    void modifyGrade(int stu_id,String course_id,int stu_score,String teacher_evaluate);

    //根据学生的id查询所有相应的成绩
    @Select("select * from grade where stu_id=#{stu_id}")
    List<Grade> SelectByStu_id(int stu_id);

    @Select("select stu_score from grade where stu_id=#{stu_id} and course_id=#{course_id}")
    int SelectScore(int stu_id,String course_id);

    @Select("select teacher_evaluate from grade where stu_id=#{stu_id} and course_id=#{course_id}")
    String SelectEva(int stu_id,String course_id);
}