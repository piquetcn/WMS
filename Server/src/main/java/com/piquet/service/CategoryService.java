package com.piquet.service;

import com.piquet.pojo.Category;
import com.piquet.pojo.PageBean;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    void delete(Integer id);

    void update(Category category);

    PageBean<Category> list(Integer pageNum, Integer pageSize, String categoryName, String categoryAcronyms);

    List<Category> findAll();

}
