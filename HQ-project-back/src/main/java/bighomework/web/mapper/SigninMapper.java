package bighomework.web.mapper;

import bighomework.web.entity.Signin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SigninMapper {
    //学生签到
    @Update("update signin set signin_state=#{signin_state} where stu_id=#{stu_id} and course_id=#{course_id}")
    void Signin(int stu_id,String course_id,int signin_state);

    //获取所有签到单的id
    @Select("select signin_id from signin")
    List<Integer> allIDs();

    //学生查询自己签到表中所有与自己相关的签到记录
    @Select("select * from signin where stu_id=#{stu_id}")
    List<Signin> allSigns(int stu_id);

    //工作人员停止签到
    @Update("update signin set signin_ok=#{signin_OK} where course_id=#{course_id}")
    void endSignin(int signin_OK,String course_id);

    //工作人员发起签到
    @Insert("insert into signin values(#{signin_id},#{stu_id},#{course_id},#{signin_OK},#{signin_state})")
    void startSignin(int signin_id,int stu_id,String course_id,int signin_OK,int signin_state);

    //查看staff是否已经发起过签到
    @Select("select count(*) from signin where course_id=#{course_id}")
    int countSignins(String course_id);
}
