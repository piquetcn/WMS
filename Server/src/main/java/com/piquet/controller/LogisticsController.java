package com.piquet.controller;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.pojo.Logistics;
import com.piquet.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logistics")
public class LogisticsController {


    @Autowired
    private LogisticsService logisticsService;

    @PostMapping
    public Result add(@RequestBody @Validated(Logistics.Add.class) Logistics logistics) {
        logisticsService.add(logistics);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        logisticsService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Logistics.Update.class) Logistics logistics) {
        logisticsService.update(logistics);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Logistics>> list (
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String LogisticsName,
            @RequestParam(required = false) Boolean state
    ) {
        PageBean<Logistics> pb = logisticsService.list(pageNum, pageSize, LogisticsName, state);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Logistics>> findAll() {
        List<Logistics> sl = logisticsService.findAll();
        return Result.success(sl);
    }
}
