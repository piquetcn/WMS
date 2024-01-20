package com.piquet.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class RelocationDetail {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull
    private Integer summary_id;
    @NotNull
    private Integer goods_id;
    @NotNull
    private Float quantity;
    @NotNull
    private Integer out_warehouse_id;
    @NotNull
    private Integer in_warehouse_id;
    @NotNull
    private Boolean state;

    private RelocationSummary relocationSummary;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
