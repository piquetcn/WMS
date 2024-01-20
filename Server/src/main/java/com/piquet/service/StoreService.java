package com.piquet.service;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Store;

import java.util.List;

public interface StoreService {
    void add(Store store);

    void delete(Integer id);

    void update(Store store);

    PageBean<Store> list(Integer pageNum, Integer pageSize, String storeName, Integer platformId, Boolean state);

    List<Store> findAll();
}
