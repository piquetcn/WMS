package com.piquet.service;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Warehouse;

public interface WarehouseService {
    void add(Warehouse warehouse);

    void delete(Integer id);

    void update(Warehouse warehouse);

    PageBean<Warehouse> list(Integer pageNum, Integer pageSize, String warehouseNum, String warehouseName, Boolean state);
}
