package bighomework.web.mapper;

import bighomework.web.entity.Teacher;
import bighomework.web.front.teacher.Teacher_Change;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    // 这里是提取出所有的id，方便我们在新增之后能够选择那个最大的数值
    @Select("select teacher_id from teacher")
    List<Integer> allId();

    // 这里是讲师的注册
    @Insert("insert into teacher values(#{teacher_id},#{teacher_name},#{teacher_position},#{teacher_email},#{teacher_tele},#{teacher_field},#{username})")
    void addTeacher(int teacher_id, String teacher_name, String teacher_position, String teacher_email,
            String teacher_tele, String teacher_field, String username);

    // 执行人修改讲师信息
    @Update("update teacher set teacher_name=#{teacher_name},teacher_position=#{teacher_position}," +
            "teacher_email=#{teacher_email},teacher_tele=#{teacher_tele},teacher_field=#{teacher_field} where teacher_id=#{teacher_id}")
    void changeTeacher(int teacher_id, String teacher_name, String teacher_position, String teacher_email,
            String teacher_tele, String teacher_field);

    // 执行人查询讲师信息
    @Select("select * from teacher")
    List<Teacher> allTeacher();

    // 执行人根据名字查询讲师信息
    @Select("select * from teacher where teacher_name=#{teacher_name}")
    List<Teacher> SelectByName(String teacher_name);

    // 执行人根据id查询讲师信息
    @Select("select * from teacher where teacher_id=#{teacher_id}")
    List<Teacher> SelectById(int teacher_id);

    // 执行人根据id和name查询讲师信息
    @Select("select * from teacher where teacher_id=#{teacher_id} and teacher_name=#{teacher_name}")
    List<Teacher> SelectByNI(int teacher_id, String teacher_name);

    // 执行人根据讲师的编号删除讲师信息
    @Delete("delete from teacher where teacher_id=#{teacher_id}")
    void DeleteById(int teacher_id);

    // 根据讲师的id查询到讲师的姓名
    @Select("select teacher_name from teacher where teacher_id=#{teacher_id}")
    String NameById(int teacher_id);

    // 根据username查询讲师的id
    @Select("select teacher_id from teacher where username=#{username}")
    int IdByUsername(String username);

    // 根据讲师的名字查找id
    @Select("select teacher_id from teacher where teacher_name=#{teacher_name}")
    int IDByName(String teacher_name);

    // 查找所有讲师的数量
    @Select("select count(*) from teacher")
    int CountTeachers();

    // 模糊查询教师ID
    @Select("SELECT teacher_id FROM teacher WHERE teacher_name LIKE CONCAT('%', #{nameCore}, '%') LIMIT 1")
    Integer findTeacherIdByFuzzyName(@Param("nameCore") String nameCore);

    @Select("SELECT * FROM teacher WHERE teacher_id = #{id}")
    Teacher selectById(@Param("id") Integer id);

}
