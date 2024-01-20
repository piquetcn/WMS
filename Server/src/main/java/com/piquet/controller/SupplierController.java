package com.piquet.controller;

import com.piquet.service.SupplierService;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.pojo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public Result add(@RequestBody @Validated(Supplier.Add.class) Supplier supplier) {
        supplierService.add(supplier);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        supplierService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Supplier.Update.class) Supplier supplier) {
        supplierService.update(supplier);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Supplier>> list (
        Integer pageNum,
        Integer pageSize,
        @RequestParam(required = false) String supplierName
    ) {
        PageBean<Supplier> pb = supplierService.list(pageNum, pageSize, supplierName);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Supplier>> findAll() {
        List<Supplier> sl = supplierService.findAll();
        return Result.success(sl);
    }
}
