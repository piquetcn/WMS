package com.piquet.controller;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.pojo.Goods;
import com.piquet.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping
    public Result add(@RequestBody @Validated(Goods.Add.class) Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        goodsService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Goods.Update.class) Goods goods) {
        goodsService.update(goods);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Goods>> list (
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String goodsCode,
            @RequestParam(required = false) String goodsName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Boolean state
    ) {
        PageBean<Goods> pb = goodsService.list(pageNum, pageSize, goodsCode, goodsName, categoryId, state);
        return Result.success(pb);
    }

    @GetMapping("/findCodeByCategoryId")
    public Result<String> findFinalCodeByCategoryId(Integer categoryId) {
        String finalGoodsCode = goodsService.findFinalCodeByCategoryId(categoryId);
        return Result.success(finalGoodsCode);
    }

    @GetMapping("/all")
    public Result<List<Goods>> findAll() {
        List<Goods> gl = goodsService.findAll();
        return Result.success(gl);
    }
}
