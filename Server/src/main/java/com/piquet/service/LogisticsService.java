package com.piquet.service;

import com.piquet.pojo.Logistics;
import com.piquet.pojo.PageBean;

import java.util.List;

public interface LogisticsService {
    void add(Logistics logistics);

    void delete(Integer id);

    void update(Logistics logistics);

    PageBean<Logistics> list(Integer pageNum, Integer pageSize, String logisticsName, Boolean state);

    List<Logistics> findAll();
}
