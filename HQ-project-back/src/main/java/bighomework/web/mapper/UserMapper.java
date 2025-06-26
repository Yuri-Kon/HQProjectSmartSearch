package bighomework.web.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 登录功能
    @Select("select count(*) from `user` where username = #{username} and password = #{password} and usertype = #{usertype}")
    int Login(String username, String password, String usertype);

    // 注册功能
    @Insert("insert into `user`(username, password, usertype) values(#{username}, #{password}, #{usertype})")
    void addUser(String username, String password, String usertype);

    // 查看某个用户名是否已经注册
    @Select("select count(*) from `user` where username = #{username}")
    int CountByUser(String username);

    // 删除某个用户名
    @Delete("delete from `user` where username = #{username}")
    void DeleteByUser(String username);
}
