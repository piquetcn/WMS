package com.piquet.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class OrderDetail {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Float quantity;
    @NotNull
    private Float price;
    @NotNull
    private Float total;
    @NotNull
    private Boolean state;

    private OrderSummary orderSummary;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
