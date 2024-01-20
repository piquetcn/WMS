package com.piquet.controller;

import com.piquet.pojo.InboundDetail;
import com.piquet.pojo.Result;
import com.piquet.service.InboundDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inboundDetail")
public class inboundDetailController {

    @Autowired
    private InboundDetailService inboundDetailService;

    @PutMapping
    public Result update(@RequestBody @Validated(InboundDetail.Update.class) InboundDetail inboundDetail) {
        inboundDetailService.update(inboundDetail);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        inboundDetailService.delete(id);
        return Result.success();
    }
}
