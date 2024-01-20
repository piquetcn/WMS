package com.piquet.service;

import com.piquet.pojo.OutboundSummary;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface OutboundSummaryService {

    Map<String, Object> create();

    void addRemark(Integer id, String remark);

    void deleteByOrderId(Integer orderId);

    boolean getExecuteByOrderId(Integer orderId);

    boolean getOutboundByOrderId(Integer orderId);
}
