package bighomework.web.mapper;

import bighomework.web.entity.Apply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicationMapper {
  @Select("select count(*) from application where username=#{username}")
  int selectByUsername(String username);

  //添加新的申请
  @Insert("insert into application values(#{apply_id},#{username},#{company_name},#{apply_budget},#{apply_want},#{stu_num},#{company_tele})")
  void addApp(int apply_id,String username,String company_name,Double apply_budget,String apply_want,int stu_num,String company_tele);

  @Select("select apply_id from application")
  List<Integer> allIDs();

  @Select("select * from application")
  List<Apply> allApps();

  //根据id得到申请
  @Select("select * from application where apply_id=#{apply_id}")
  Apply getApp(int apply_id);

}
