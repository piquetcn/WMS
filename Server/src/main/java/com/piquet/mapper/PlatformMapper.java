package com.piquet.mapper;

import com.piquet.pojo.Platform;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlatformMapper {

    @Insert("INSERT INTO t_platform (platform_name, remarks) " +
            "VALUES (#{platformName}, #{remarks})")
    void add(Platform platform);

    @Delete("DELETE FROM t_platform WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_platform SET " +
            "platform_name=#{platformName}, remarks=#{remarks} WHERE id=#{id}")
    void update(Platform platform);

    List<Platform> list(String platformName);

    @Select("SELECT * FROM t_platform")
    List<Platform> findAll();
}
