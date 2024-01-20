package com.piquet.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Store {

    @NotNull(groups = Supplier.Update.class)
    private Integer id;
    @NotNull
    private Integer platformId;
    @NotEmpty
    private String storeName;
    private Boolean state;
    private String remarks;

    public interface Add extends Default {}
    public interface Update extends Default {}

}
