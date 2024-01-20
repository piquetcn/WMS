package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.CategoryMapper;
import com.piquet.pojo.Category;
import com.piquet.pojo.PageBean;
import com.piquet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public PageBean<Category> list(Integer pageNum, Integer pageSize, String categoryName, String categoryAcronyms) {
        PageBean<Category> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Category> cl = categoryMapper.list(categoryName, categoryAcronyms);
        Page<Category> p = (Page<Category>) cl;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Category> findAll() {
        List<Category> cl = categoryMapper.findAll();
        return cl;
    }

}
