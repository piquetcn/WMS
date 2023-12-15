package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Warehouse {

    @NotNull(groups = Update.class)
    private Integer id;                 //主键id
    @NotEmpty(groups = Add.class)
    private String warehouseCode;        //仓库编码
    @NotEmpty
    private String warehouseName;       //仓库名称
    private String warehouseSize;       //仓库规格
    private String warehousePic;        //仓库图片
    private String warehouseLocation;   //仓库位置
    private Boolean state;              //仓库状态[0:停用|1:正常]
    private String remarks;              //备注

    public interface Add extends Default {}
    public interface Update extends Default {}
}
