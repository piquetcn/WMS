package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Supplier {

    @NotNull(groups = Update.class)
    private Integer id;             //主键id
    @NotEmpty
    private String supplierName;    //供应商名称
    private String supplierAddr;    //供应商地址
    private String contacts;        //联系人
    @NotEmpty
    private String phone;           //联系电话
    private String remarks;         //备注

    public interface Add extends Default {}
    public interface Update extends Default {}
}
