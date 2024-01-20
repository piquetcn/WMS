package com.piquet.mapper;

import com.piquet.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("INSERT INTO t_category (category_name, category_acronyms) " +
            "VALUES (#{categoryName}, #{categoryAcronyms})")
    void add(Category category);

    @Delete("DELETE FROM t_category WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_category set " +
            "category_name=#{categoryName}, category_acronyms=#{categoryAcronyms} WHERE id=#{id}")
    void update(Category category);

    List<Category> list(String categoryName, String categoryAcronyms);

    @Select("SELECT * FROM t_category")
    List<Category> findAll();

}
