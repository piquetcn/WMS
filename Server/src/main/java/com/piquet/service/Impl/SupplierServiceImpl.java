package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.SupplierMapper;
import com.piquet.service.SupplierService;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void add(Supplier supplier) {
        supplierMapper.add(supplier);
    }

    @Override
    public void delete(Integer id) {
        supplierMapper.delete(id);
    }

    @Override
    public void update(Supplier supplier) {
        supplierMapper.update(supplier);
    }

    @Override
    public PageBean<Supplier> list(Integer pageNum, Integer pageSize, String supplierName) {
        PageBean<Supplier> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Supplier> sl = supplierMapper.list(supplierName);
        Page<Supplier> p = (Page<Supplier>) sl;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> sl = supplierMapper.findAll();
        return sl;
    }
}
