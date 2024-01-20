package com.piquet.service;

import com.piquet.pojo.Goods;
import com.piquet.pojo.PageBean;

import java.util.List;

public interface GoodsService {
    void add(Goods goods);

    void delete(Integer id);

    void update(Goods goods);

    PageBean<Goods> list(Integer pageNum, Integer pageSize, String goodsCode, String goodsName, Integer categoryId, Boolean state);

    String findFinalCodeByCategoryId(Integer categoryId);

    List<Goods> findAll();
}
