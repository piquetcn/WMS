package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.WarehouseMapper;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Warehouse;
import com.piquet.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public void add(Warehouse warehouse) {
        warehouseMapper.add(warehouse);
    }

    @Override
    public void delete(Integer id) {
        warehouseMapper.delete(id);
    }

    @Override
    public void update(Warehouse warehouse) {
        warehouseMapper.update(warehouse);
    }

    @Override
    public PageBean<Warehouse> list(Integer pageNum, Integer pageSize, String warehouseCode, String warehouseName, Boolean state) {
        PageBean<Warehouse> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Warehouse> as = warehouseMapper.list(warehouseCode, warehouseName, state);
        Page<Warehouse> p = (Page<Warehouse>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> wl = warehouseMapper.findAll();
        return wl;
    }
}
