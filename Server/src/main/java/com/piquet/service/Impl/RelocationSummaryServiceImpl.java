package com.piquet.service.Impl;

import com.piquet.mapper.RelocationSummaryMapper;
import com.piquet.service.RelocationSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelocationSummaryServiceImpl implements RelocationSummaryService {

    @Autowired
    private RelocationSummaryMapper relocationSummaryMapper;
}
