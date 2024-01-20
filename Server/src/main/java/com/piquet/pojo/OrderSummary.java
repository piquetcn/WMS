package com.piquet.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderSummary {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotNull
    private Integer storeId;
    @NotEmpty
    private String orderCode;
    @NotEmpty
    private String recipientName;
    @NotEmpty
    private String recipientPhone;
    @NotEmpty
    private String recipientAddress;
    private String remarks;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @NotNull
    private Boolean state;

    private List<OrderDetail> orderDetailList;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
