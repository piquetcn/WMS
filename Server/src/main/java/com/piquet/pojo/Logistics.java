package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Logistics {

    @NotNull(groups = Supplier.Update.class)
    private Integer id;
    @NotEmpty
    private String logisticsName;
    private String contacts;
    private String phone;
    private String address;
    private Boolean state;
    private String remarks;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
