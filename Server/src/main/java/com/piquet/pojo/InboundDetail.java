package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
@Data
public class InboundDetail {

    @NotNull(groups = Update.class)
    private Integer id;             //主键id
    @NotNull
    private Integer summaryId;      //入库单id
    @NotNull
    private Integer goodsId;        //商品编码
    @NotNull
    private Float quantity;         //数量
    @NotNull
    private Float price;            //单价
    @NotNull
    private Float total;            //总金额
    @NotNull
    private Integer warehouseId;    //仓库ID
    @NotNull
    private Boolean state;          //入库状态[0:未入库|1:已入库]

    private InboundSummary inboundSummary;

    public interface Add extends Default {}
    public interface Update extends Default {}
}