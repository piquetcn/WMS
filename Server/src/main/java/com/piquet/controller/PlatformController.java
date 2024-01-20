package com.piquet.controller;

import com.piquet.pojo.Platform;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform")
public class PlatformController {


    @Autowired
    private PlatformService platformService;

    @PostMapping
    public Result add(@RequestBody @Validated(Platform.Add.class) Platform platform) {
        platformService.add(platform);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        platformService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Platform.Update.class) Platform platform) {
        platformService.update(platform);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Platform>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String platformName
    ) {
        PageBean<Platform> pb = platformService.list(pageNum, pageSize, platformName);
        return Result.success(pb);
    }

    @GetMapping("/all")
    public Result<List<Platform>> findAll() {
        List<Platform> cl = platformService.findAll();
        return Result.success(cl);
    }

}
