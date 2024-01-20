package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.GoodsMapper;
import com.piquet.pojo.Goods;
import com.piquet.pojo.PageBean;
import com.piquet.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void add(Goods goods) {
        goodsMapper.add(goods);
    }

    @Override
    public void delete(Integer id) {
        goodsMapper.delete(id);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.update(goods);
    }

    @Override
    public PageBean<Goods> list(Integer pageNum, Integer pageSize, String goodsNum, String goodsCode, Integer categoryId, Boolean state) {
        PageBean<Goods> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Goods> gs = goodsMapper.list(goodsNum, goodsCode, categoryId, state);
        Page<Goods> p = (Page<Goods>) gs;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public String findFinalCodeByCategoryId(Integer categoryId) {
        String finalGoodsCode = goodsMapper.findFinalCodeByCategoryId(categoryId);
        return finalGoodsCode;
    }

    @Override
    public List<Goods> findAll() {
        List<Goods> gl = goodsMapper.findAll();
        return gl;
    }

}
