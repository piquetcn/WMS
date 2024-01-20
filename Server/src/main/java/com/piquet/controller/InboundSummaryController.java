package com.piquet.controller;

import com.piquet.pojo.*;
import com.piquet.service.InboundSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inboundSummary")
public class InboundSummaryController {

    @Autowired
    private InboundSummaryService inboundSummaryService;

    @PostMapping
    public Result add(@RequestBody @Validated(InboundSummary.Add.class) InboundSummary inboundSummary) {
        inboundSummaryService.add(inboundSummary);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        inboundSummaryService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(InboundSummary.Update.class) InboundSummary inboundSummary) {

        inboundSummaryService.update(inboundSummary);

        return Result.success();
    }

    @GetMapping
    public Result<PageBean<InboundSummary>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String inboundCode,
            @RequestParam(required = false) Integer supplierId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        PageBean<InboundSummary> pb = inboundSummaryService.list(pageNum, pageSize, inboundCode, supplierId, startDate, endDate);
        return Result.success(pb);
    }

    @GetMapping("/findFinalCode")
    public Result<String> findFinalCode() {
        String code = inboundSummaryService.findFinalCode();
        return Result.success(code);
    }
}
