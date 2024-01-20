package com.piquet.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OutboundSummary {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty
    private String outboundCode;
    private Integer operator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime executeTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outboundTime;
    private Boolean execute;
    private Boolean outbound;
    @NotNull
    private Boolean state;
    private String remarks;

    private List<OutboundDetail> outboundDetailList;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
