package com.piquet.service;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Supplier;

public interface SupplierService {

    void add(Supplier supplier);

    void delete(Integer id);

    void update(Supplier supplier);

    PageBean<Supplier> list(Integer pageNum, Integer pageSize, String supplierName);
}
