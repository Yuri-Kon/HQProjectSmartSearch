package bighomework.web.mapper;

import bighomework.web.entity.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportMapper {
    //查询某个日报表是否已经生成
    @Select("select count(*) from report where report_start=#{report_start}")
    int CountByTime(String report_start);

    @Select("select report_id from report where report_start=#{report_start}")
    int IdByTime(String report_start);

    //如果日报表没生成，那就insert新的日报表
    @Insert("insert into report values(#{report_id},#{report_start},#{report_end},#{report_income},#{teacher_num}," +
            "#{course_num},#{stu_num},#{company_num},#{executor_num})")
    void addReport(int report_id,String report_start,String report_end,double report_income,int teacher_num,int course_num,
                   int stu_num,int company_num,int executor_num);

    //如果日报表已经生成那就修改
    @Update("update report set report_income=#{report_income},teacher_num=#{teacher_num},course_num=#{course_num}," +
            "stu_num=#{stu_num},company_num=#{company_num},executor_num=#{executor_num} where report_id=#{report_id}")
    void changeReport(int report_id,double report_income,int teacher_num,int course_num,
                      int stu_num,int company_num,int executor_num);

    @Select("select report_id from report")
    List<Integer> allIDs();

    @Select("select * from report")
    List<Report> allReports();

    //根据id查找报表
    @Select("select * from report where report_id=#{report_id}")
    List<Report> chooseTheReport(int report_id);

    //根据start和end进行模糊查询

}
