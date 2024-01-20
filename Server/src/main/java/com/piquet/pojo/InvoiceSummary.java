package com.piquet.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceSummary {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer outboundId;
    @NotNull
    private Integer abstractId;

    private Integer logisticsId;

    private String logisticsCode;

    private Boolean invoice;
    private LocalDateTime invoice_time;
    @NotNull
    private Boolean state;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
