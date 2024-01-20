package com.piquet.controller;

import com.piquet.service.RelocationSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relocationSummary")
public class RelocationSummaryController {

    @Autowired
    private RelocationSummaryService relocationSummaryService;
}
