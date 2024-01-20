package com.piquet.service;

import com.piquet.pojo.OrderSummary;
import com.piquet.pojo.PageBean;

public interface OrderSummaryService {
    void add(OrderSummary orderSummary);

    void delete(Integer id);

    void update(OrderSummary orderSummary);

    PageBean<OrderSummary> list(Integer pageNum, Integer pageSize, String orderCode, String recipientName, String recipientPhone, String recipientAddress, String startCreateTime, String endCreateTime, String startPaymentTime, String endPaymentTime, String startUpdateTime, String endUpdateTime, Boolean state);
}
