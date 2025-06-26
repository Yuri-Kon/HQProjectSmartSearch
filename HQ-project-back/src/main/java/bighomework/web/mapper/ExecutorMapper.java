package bighomework.web.mapper;

import bighomework.web.entity.Executor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExecutorMapper {
    //查询执行人的编号，看有没有重复的
    @Select("select count(*) from executor where exe_id=#{exe_id}")
    int CountByExe_id(String exe_id);

    @Insert("insert into executor values(#{exe_id},#{exe_name},#{username})")
    void addExecutor(String exe_id,String exe_name,String username);

    //根据执行人的username查看执行人的编号
    @Select("select exe_id from executor where username=#{username}")
    String exe_id(String username);

    @Select("select count(*) from executor")
    int executor_num();

    @Select("select * from executor")
    List<Executor> FindAll();
}
