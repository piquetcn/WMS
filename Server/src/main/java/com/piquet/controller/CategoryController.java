package com.piquet.controller;

import com.piquet.pojo.Category;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Category>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String categoryAcronyms
    ) {
        PageBean<Category> pb = categoryService.list(pageNum, pageSize, categoryName, categoryAcronyms);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Category>> findAll() {
        List<Category> cl = categoryService.findAll();
        return Result.success(cl);
    }

}