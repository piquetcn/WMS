package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.LogisticsMapper;
import com.piquet.pojo.Logistics;
import com.piquet.pojo.PageBean;
import com.piquet.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsServiceImpl implements LogisticsService {
    
    @Autowired
    private LogisticsMapper logisticsMapper;
    
    @Override
    public void add(Logistics logistics) {
        logisticsMapper.add(logistics);
    }

    @Override
    public void delete(Integer id) {
        logisticsMapper.delete(id);
    }

    @Override
    public void update(Logistics logistics) {
        logisticsMapper.update(logistics);
    }

    @Override
    public PageBean<Logistics> list(Integer pageNum, Integer pageSize, String logisticsName, Boolean state) {
        PageBean<Logistics> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Logistics> ll = logisticsMapper.list(logisticsName, state);
        Page<Logistics> p = (Page<Logistics>) ll;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Logistics> findAll() {
        List<Logistics> ll = logisticsMapper.findAll();
        return ll;
    }
}
