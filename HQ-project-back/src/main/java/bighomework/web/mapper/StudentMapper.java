package bighomework.web.mapper;

import bighomework.web.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    //这里是根据学生的用户名来查询学生信息
    @Select("select * from student where username=#{username}")
    List<Student> SelectByUsername(String username);

    @Select("select stu_id from student")
    List<Integer> allStu_id();

    //这是对未绑定的学生信息进行绑定
    @Insert("insert into student values(#{stu_id},#{username},#{stu_name},#{stu_company},#{stu_position},#{stu_level}," +
            "#{stu_email},#{stu_tele},#{stu_state})")
    void addStudent(int stu_id,String username,String stu_name,String stu_company,String stu_position,String stu_level,
                    String stu_email,String stu_tele,int stu_state);

    //根据username查看学生的id
    @Select("select stu_id from student where username=#{username}")
    int IdByUsername(String username);

    @Update("update student set stu_name=#{stu_name},stu_company=#{stu_company},stu_position=#{stu_position},stu_level=#{stu_level}" +
            ",stu_email=#{stu_email},stu_tele=#{stu_tele},stu_state=#{stu_state} where stu_id=#{stu_id}")
    void modifyStudent(int stu_id,String stu_name,String stu_company,String stu_position,String stu_level,
                       String stu_email,String stu_tele,int stu_state);

    //根据公司名称找到所有对应的学生
    @Select("select stu_id from student where stu_company=#{company_name}")
    List<Integer> StudentsByCompany(String company_name);

    //根据学生id查询学生姓名
    @Select("select stu_name from student where stu_id=#{stu_id}")
    String NameById(int stu_id);

    @Select("select count(*) from student")
    int stu_num();

    @Select("select * from student where stu_id=#{stu_id}")
    List<Student> SelectById(int stu_id);

    @Delete("delete from student where stu_id=#{stu_id}")
    void deleteStudent(int stu_id);

    @Select("select checkstate from student where username=#{username} and course_id=#{course_id}")
    int checkStateByUsername(String username,Integer course_id);

    @Select("select * from student")
    List<Student> allStu();
}
