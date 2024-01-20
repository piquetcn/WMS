package com.piquet.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class OutboundDetail {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull
    private Integer summaryId;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Float quantity;
    @NotNull
    private Integer warehouseId;
    @NotNull
    private Boolean state;

    private OutboundSummary outboundSummary;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
