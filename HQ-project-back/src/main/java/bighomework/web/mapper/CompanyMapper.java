package bighomework.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyMapper {
  //查询公司的名字，看是否有重复的
  @Select("select count(*) from company where company_name=#{company_name}")
  int CountByName(String company_name);

  @Select("select company_key from company where company_name=#{company_name}")
  String SelectByName(String company_name);

  //这里是新增公司
  @Insert("insert into company values(#{company_name},#{company_key},#{username},#{company_tele})")
  void addCompany(String company_name,String company_key,String username,String company_tele);

  //根据用户名查看对应的公司名字
  @Select("select company_name from company where username=#{username}")
  String NameByUsername(String username);

  @Select("select count(*) from company")
  int company_num();

}
