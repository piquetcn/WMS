package com.piquet.controller;

import com.piquet.pojo.Category;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(Category category) {
        categoryService.update(category);
        return Result.success();
    }

    public Result<PageBean<Category>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String categoryAcronyms
    ) {
        PageBean<Category> pb = categoryService.list(pageNum, pageSize, categoryName, categoryAcronyms);
        return Result.success(pb);
    }
}