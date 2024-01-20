package com.piquet.controller;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.pojo.Warehouse;
import com.piquet.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public Result add(@RequestBody @Validated(Warehouse.Add.class) Warehouse warehouse) {
        warehouseService.add(warehouse);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        warehouseService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Warehouse.Update.class) Warehouse warehouse) {
        warehouseService.update(warehouse);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Warehouse>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String warehouseCode,
            @RequestParam(required = false) String warehouseName,
            @RequestParam(required = false) Boolean state
    ) {
        PageBean<Warehouse> pb = warehouseService.list(pageNum, pageSize, warehouseCode, warehouseName, state);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Warehouse>> findAll() {
        List<Warehouse> wl = warehouseService.findAll();
        return Result.success(wl);
    }
}
