package com.piquet.mapper;

import com.piquet.pojo.Abstract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AbstractMapper {

    @Select("SELECT * FROM t_abstract")
    List<Abstract> findAll();
}
