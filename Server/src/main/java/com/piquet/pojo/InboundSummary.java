package com.piquet.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InboundSummary {

    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty
    private String inboundCode;             //入库单号
    @NotNull
    private Integer supplierId;             //供应商ID
    private Integer operator;               //操作员(用户)ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;        //制表时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;       //更新时间
    private String remarks;                 //备注

    private List<InboundDetail> inboundDetailList;  //入库明细列表

    public interface Add extends Default {}
    public interface Update extends Default {}
}
