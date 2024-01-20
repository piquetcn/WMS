package com.piquet.service.Impl;

import com.piquet.mapper.InvoiceSummaryMapper;
import com.piquet.service.InvoiceSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceSummaryServiceImpl implements InvoiceSummaryService {

    @Autowired
    private InvoiceSummaryMapper invoiceSummaryMapper;

    @Override
    public Integer matchOutboundIdByOrderId(Integer orderId) {
        Integer outboundId = invoiceSummaryMapper.matchOutboundIdByOrderId(orderId);
        return outboundId;
    }
}
