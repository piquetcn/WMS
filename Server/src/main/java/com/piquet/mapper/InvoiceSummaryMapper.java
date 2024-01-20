package com.piquet.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InvoiceSummaryMapper {

    @Select("SELECT outbound_id FROM t_invoice_summary WHERE order_id={orderId}")
    Integer matchOutboundIdByOrderId(Integer orderId);
}
