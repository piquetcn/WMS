package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.StoreMapper;
import com.piquet.pojo.Store;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Store;
import com.piquet.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    
    @Autowired
    private StoreMapper storeMapper;
    
    @Override
    public void add(Store store) {
        storeMapper.add(store);
    }

    @Override
    public void delete(Integer id) {
        storeMapper.delete(id);
    }

    @Override
    public void update(Store store) {
        storeMapper.update(store);
    }

    @Override
    public PageBean<Store> list(Integer pageNum, Integer pageSize, String storeName, Integer platformId, Boolean state) {
        PageBean<Store> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Store> ll = storeMapper.list(storeName, platformId, state);
        Page<Store> p = (Page<Store>) ll;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Store> findAll() {
        List<Store> sl = storeMapper.findAll();
        return sl;
    }
}
