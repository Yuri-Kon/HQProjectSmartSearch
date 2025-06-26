package bighomework.web.mapper;

import bighomework.web.entity.Bill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BillMapper {
  //新增账单
  @Insert("insert into bill values(#{bill_id},#{bill_time},#{course_id},#{username},#{bill_income})")
  void addBill(int bill_id,String bill_time,String course_id,String username,double bill_income);

  //获取所有的账单编号，以随机生成新的账单编号
  @Select("select bill_id from bill")
  List<Integer> allIDs();

  //经理需要获取所有的账单
  @Select("select * from bill")
  List<Bill> allBills();

  //根据id返回对应的账单
  @Select("select * from bill where bill_id=#{bill_id}")
  List<Bill> BillsByIDs(int bill_id);

  //根据日期返回对应的账单
  @Select("select bill_income from bill where bill_time=#{bill_time}")
  List<Double> Moneys(String bill_time);
}
