package com.piquet.service;

import com.piquet.pojo.Category;
import com.piquet.pojo.PageBean;

public interface CategoryService {
    void add(Category category);

    void delete(Integer id);

    void update(Category category);

    PageBean<Category> list(Integer pageNum, Integer pageSize, String categoryName, String categoryAcronyms);
}
