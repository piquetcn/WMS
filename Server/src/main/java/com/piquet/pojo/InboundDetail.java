package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class InboundDetail {

    private Integer id;         //主键id
    @NotNull
    private Integer summaryId;   //入库单id
    @NotEmpty
    private String goodsNum;     //商品编码
    @NotNull
    private Float quantity;     //数量
    @NotNull
    private Float price;        //单价
    @NotNull
    private Float total;        //总金额
    @NotEmpty
    private String warehouseNum; //仓库编码
    @NotNull
    private Boolean state;      //入库状态[0:未入库|1:已入库]

    private InboundSummary inboundSummary;
}