package bighomework.web.mapper;

import bighomework.web.entity.Evaluate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EvaluateMapper {

    //查询评价表中的所有编号
    @Select("select eva_id from evaluate")
    List<Integer> allIDs();

    //学生新增课程评价
    @Insert("insert into evaluate values(#{eva_id},#{course_id},#{stu_id},#{teacher_id},#{eva_score},#{eva_content})")
    void addEvaluate(int eva_id,String course_id,int stu_id,int teacher_id,int eva_score,String eva_content);

    //学生查询课程评价，根据course_id和stu_id
    @Select("select * from evaluate where stu_id=#{stu_id} and course_id=#{course_id}")
    Evaluate SearchBySCID(int stu_id,String course_id);

    //查询这个学生是否已经评价过该课程，根据course_id和stu_id
    @Select("select count(*) from evaluate where stu_id=#{stu_id} and course_id=#{course_id}")
    int CountTheEva(int stu_id,String course_id);

    //学生修改课程评价
    @Update("update evaluate set eva_score=#{eva_score},eva_content=#{eva_content} where stu_id=#{stu_id} and course_id=#{course_id}")
    void ModifyTheEva(int stu_id,String course_id,int eva_score,String eva_content);

    //根据讲师的id查询课程评价
    @Select("select * from evaluate where teacher_id=#{teacher_id}")
    List<Evaluate> SelectByT_id(int teacher_id);
}
