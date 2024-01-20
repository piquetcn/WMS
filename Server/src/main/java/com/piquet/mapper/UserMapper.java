package com.piquet.mapper;

import com.piquet.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into t_user(username, password, create_time, update_time)" +
            " values(#{username}, #{md5String}, now(), now())")
    void add(String username, String md5String);

    @Select("SELECT id, nickname FROM t_user")
    List<User> nicknameList();
}
