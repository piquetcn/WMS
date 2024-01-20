package com.piquet.controller;

import com.piquet.pojo.OrderDetail;
import com.piquet.pojo.Result;
import com.piquet.service.OutboundDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OutboundDetailService outboundDetailService;

    @GetMapping
    private Result test() {
        Integer outboundId = 2000;

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderId(2000);
        orderDetail.setGoodsId(2);
        orderDetail.setQuantity(50f);
        orderDetail.setPrice(8.0f);
        orderDetail.setTotal(400.0f);
        orderDetail.setState(true);

        outboundDetailService.add(outboundId, orderDetail);

        return Result.success();
    }
}
