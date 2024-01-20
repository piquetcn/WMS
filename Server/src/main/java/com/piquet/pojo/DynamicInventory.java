package com.piquet.pojo;

import lombok.Data;

@Data
public class DynamicInventory {

    private Integer goodsId;
    private Integer warehouseId;
    private Float totalInbound;
    private Float totalOutbound;
    private Float inventory;
}
