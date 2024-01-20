package com.piquet.controller;

import com.piquet.pojo.OrderSummary;
import com.piquet.pojo.PageBean;
import com.piquet.pojo.Result;
import com.piquet.service.OrderSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderSummary")
public class OrderSummaryController {

    @Autowired
    private OrderSummaryService orderSummaryService;

    @PostMapping
    private Result add(@RequestBody @Validated(OrderSummary.Add.class) OrderSummary orderSummary) {
        orderSummaryService.add(orderSummary);
        return Result.success();
    }

    @DeleteMapping
    private Result delete(Integer id) {
        orderSummaryService.delete(id);
        return Result.success();
    }

    @PutMapping
    private Result update(@RequestBody @Validated(OrderSummary.Update.class) OrderSummary orderSummary) {
        orderSummaryService.update(orderSummary);
        return Result.success();
    }

    @GetMapping
    private Result<PageBean<OrderSummary>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String orderCode,
            @RequestParam(required = false) String recipientName,
            @RequestParam(required = false) String recipientPhone,
            @RequestParam(required = false) String recipientAddress,
            @RequestParam(required = false) String startCreateTime,
            @RequestParam(required = false) String endCreateTime,
            @RequestParam(required = false) String startPaymentTime,
            @RequestParam(required = false) String endPaymentTime,
            @RequestParam(required = false) String startUpdateTime,
            @RequestParam(required = false) String endUpdateTime,
            @RequestParam(required = false) Boolean state
    ) {
        PageBean<OrderSummary> pb = orderSummaryService.list(pageNum, pageSize, orderCode, recipientName, recipientPhone,
                recipientAddress, startCreateTime, endCreateTime, startPaymentTime, endPaymentTime, startUpdateTime, endUpdateTime, state);
        return Result.success(pb);
    }
}
