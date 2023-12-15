package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Goods {

    @NotNull(groups = Update.class)
    private Integer id;                 //主键id
    @NotEmpty
    private String goodsCode;           //商品编码
    @NotEmpty
    private String goodsName;           //商品名称
    @NotEmpty
    private String goodsSpec;           //商品规格
    @NotEmpty
    private String goodsUnit;           //商品单位
    @NotNull
    private Integer categoryId;         //商品分类
    private String goodsPic;            //商品图片
    private String manufacturer;        //生产厂家
    @NotNull
    private Integer minInventory;       //最小库存
    @NotNull
    private Boolean state;              //商品状态[0:已下架|1:在售中]
    private String remarks;             //备注

    public interface Add extends Default {}
    public interface Update extends Default {}
}
