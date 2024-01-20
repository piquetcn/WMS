package com.piquet.service.Impl;

import com.piquet.mapper.OrderDetailMapper;
import com.piquet.mapper.OrderSummaryMapper;
import com.piquet.pojo.OrderDetail;
import com.piquet.pojo.OrderSummary;
import com.piquet.pojo.PageBean;
import com.piquet.service.OrderSummaryService;
import com.piquet.service.OutboundDetailService;
import com.piquet.service.OutboundSummaryService;
import com.piquet.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderSummaryServiceImpl implements OrderSummaryService {

    @Autowired
    private OrderSummaryMapper orderSummaryMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OutboundSummaryService outboundSummaryService;

    @Autowired
    private OutboundDetailService outboundDetailService;

    @Override
    public void add(OrderSummary orderSummary) {
        if (orderSummary.getCreateTime().toString().isEmpty()) {
            orderSummary.setCreateTime(LocalDateTime.now());
        }
        if (orderSummary.getPaymentTime().toString().isEmpty()) {
            orderSummary.setPaymentTime(orderSummary.getCreateTime());
        }
        orderSummary.setUpdateTime(orderSummary.getPaymentTime());

        orderSummaryMapper.add(orderSummary);

        Map<String, Object> map = outboundSummaryService.create();

        Integer outboundId = (Integer) map.get("id");

        if (! orderSummary.getOrderDetailList().isEmpty()) {
            Integer orderId = orderSummaryMapper.findIdByOrderCode(orderSummary.getOrderCode());

            for (OrderDetail orderDetail:
                    orderSummary.getOrderDetailList()) {
                orderDetail.setOrderId(orderId);
                orderDetailMapper.add(orderDetail);

                outboundDetailService.add(outboundId, orderDetail);
            }
        }

    }

    @Override
    public void delete(Integer id) {
        orderSummaryMapper.delete(id);
        orderDetailMapper.deleteByOrderId(id);

        outboundSummaryService.deleteByOrderId(id);
        outboundDetailService.deleteByOrderId(id);
    }

    @Override
    public void update(OrderSummary orderSummary) {
        if (! orderSummary.getState()) {
            throw new RuntimeException("订单已关闭!");
        }
        if (outboundSummaryService.getExecuteByOrderId(orderSummary.getId())) {
            throw new RuntimeException("订单已执行!");
        } else if (outboundSummaryService.getOutboundByOrderId(orderSummary.getId())) {
            throw new RuntimeException("订单已出库!");
        }

        orderSummary.setUpdateTime(LocalDateTime.now());
        orderSummaryMapper.update(orderSummary);

        orderDetailMapper.deleteByOrderId(orderSummary.getId());
        outboundDetailService.
        for (OrderDetail orderDetail:
                orderSummary.getOrderDetailList()) {
            orderDetail.setOrderId(orderSummary.getId());
            orderDetailMapper.add(orderDetail);

            outboundDetailService.add();
        }
    }

    @Override
    public PageBean<OrderSummary> list(Integer pageNum, Integer pageSize, String orderCode, String recipientName, String recipientPhone, String recipientAddress, String startCreateTime, String endCreateTime, String startPaymentTime, String endPaymentTime, String startUpdateTime, String endUpdateTime, Boolean state) {

        startCreateTime = startCreateTime == null ? LocalDateTime.of(2000, 1, 1, 0, 0).toString() : DateTimeUtil.formatDateTime(startCreateTime);
        endCreateTime = endCreateTime == null ? LocalDateTime.now().toString() : DateTimeUtil.formatDateTime(endCreateTime);

        startPaymentTime = startPaymentTime == null ? LocalDateTime.of(2000, 1, 1, 0, 0).toString() : DateTimeUtil.formatDateTime(startPaymentTime);
        endPaymentTime = endPaymentTime == null ? LocalDateTime.now().toString() : DateTimeUtil.formatDateTime(endPaymentTime);

        startUpdateTime = startUpdateTime == null ? LocalDateTime.of(2000, 1, 1, 0, 0).toString() : DateTimeUtil.formatDateTime(startUpdateTime);
        endUpdateTime = endUpdateTime == null ? LocalDateTime.now().toString() : DateTimeUtil.formatDateTime(endUpdateTime);

        List<OrderSummary> osl = orderSummaryMapper.list(pageNum, pageSize, orderCode, recipientName, recipientPhone, recipientAddress, startCreateTime, endCreateTime, startPaymentTime, endPaymentTime, startUpdateTime, endUpdateTime, state);

        Integer startIdx = (pageNum - 1) * pageSize;
        Integer endIdx = Math.min(startIdx + pageSize, osl.size());

        List<OrderSummary> pagedList = osl.subList(startIdx, endIdx);

        PageBean<OrderSummary> pb = new PageBean<>();
        Long total = Long.valueOf(osl.size());
        pb.setTotal(total);
        pb.setItems(pagedList);

        return pb;
    }
}
