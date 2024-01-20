package com.piquet.controller;

import com.piquet.pojo.Result;
import com.piquet.service.OutboundSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outboundSummary")
public class OutboundSummaryController {

    @Autowired
    private OutboundSummaryService outboundSummaryService;

}
