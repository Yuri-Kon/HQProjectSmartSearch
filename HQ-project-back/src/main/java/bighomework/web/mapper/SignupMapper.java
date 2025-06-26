package bighomework.web.mapper;

import bighomework.web.entity.Signup;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface SignupMapper {
    //根据一个学生的id来查看这个学生选的所有课程
    @Select("select course_id from signup where stu_id=#{stu_id}")
    List<String> CoursesByStu_id(int stu_id);

    //根据stu_id和course_id来检查这个学生是否已经选了某门课
    @Select("select count(*) from signup where stu_id=#{stu_id} and course_id=#{course_id}")
    int CountByTwoIDs(int stu_id,String course_id);

    //查看选课表中所有的编号，用于生成随机id
    @Select("select signup_id from signup")
    List<Integer> allIDS();

    //学生选课
    @Insert("insert into signup values(#{signup_id},#{stu_id},#{course_id},#{signup_state})")
    void addSignup(int signup_id,int stu_id,String course_id,int signup_state);

    //学生退课
    @Delete("delete from signup where stu_id=#{stu_id} and course_id=#{course_id}")
    void dropSignup(int stu_id,String course_id);

    //根据学生的id来查看学生选的所有课及其状态
    @Select("select * from signup where stu_id=#{stu_id}")
    List<Signup> allSignup(int stu_id);

    //学生缴费
    @Update("update signup set signup_state=#{signup_state} where stu_id=#{stu_id} and course_id=#{course_id}")
    void pay(int stu_id,String course_id,int signup_state);

    //对于某一门课程，查看学生是否已经缴纳过费用
    @Select("select signup_state from signup where stu_id=#{stu_id} and course_id=#{course_id}")
    int state(int stu_id,String course_id);

    //根据某一门课程的id返回所有选择这门课程的学生
    @Select("select stu_id from signup where course_id=#{course_id}")
    List<Integer> stu_ids(String course_id);
}
