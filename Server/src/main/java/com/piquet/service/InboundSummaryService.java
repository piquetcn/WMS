package com.piquet.service;

import com.piquet.pojo.InboundDetail;
import com.piquet.pojo.InboundSummary;
import com.piquet.pojo.PageBean;

import java.util.List;

public interface InboundSummaryService {

    void add(InboundSummary inboundSummary);

    void delete(Integer id);

    PageBean<InboundSummary> list(Integer pageNum, Integer pageSize, String inboundNum, String supplierName, String startDate, String endDate);

    void update(InboundSummary inboundSummary);
}
