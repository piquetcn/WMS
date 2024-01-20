package com.piquet.service;

import com.piquet.pojo.OrderDetail;

public interface OutboundDetailService {
    void add(Integer outboundId, OrderDetail orderDetail);

    void deleteByOrderId(Integer orderId);
}
