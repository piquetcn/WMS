package com.piquet.service.Impl;

import com.piquet.mapper.DynamicInventoryMapper;
import com.piquet.pojo.DynamicInventory;
import com.piquet.service.DynamicInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicInventoryServiceImpl implements DynamicInventoryService {

    @Autowired
    private DynamicInventoryMapper dynamicInventoryMapper;

    @Override
    public Integer matchWarehouse(Integer goodsId, Float quantity){
        List<DynamicInventory> dil = dynamicInventoryMapper.findByGoodsId(goodsId);

        Integer warehouseId = 0;
        for (DynamicInventory dynamicInventory:
             dil) {
            if (dynamicInventory.getInventory() > quantity) {
                warehouseId = dynamicInventory.getWarehouseId();
            }
        }

        if (warehouseId.equals(0)) {
            throw new RuntimeException("库存不足!");
        }

        return warehouseId;
    }
}
