package com.piquet.controller;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.pojo.Store;
import com.piquet.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public Result add(@RequestBody @Validated(Store.Add.class) Store store) {
        storeService.add(store);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        storeService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Store.Update.class) Store store) {
        storeService.update(store);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Store>> list (
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer platformId,
            @RequestParam(required = false) Boolean state
    ) {
        PageBean<Store> pb = storeService.list(pageNum, pageSize, storeName, platformId, state);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Store>> findAll() {
        List<Store> sl = storeService.findAll();
        return Result.success(sl);
    }
}
