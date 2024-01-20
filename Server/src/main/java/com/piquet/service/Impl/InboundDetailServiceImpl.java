package com.piquet.service.Impl;

import com.piquet.mapper.InboundDetailMapper;
import com.piquet.mapper.InboundSummaryMapper;
import com.piquet.pojo.InboundDetail;
import com.piquet.pojo.InboundSummary;
import com.piquet.service.InboundDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InboundDetailServiceImpl implements InboundDetailService {

    @Autowired
    private InboundSummaryMapper inboundSummaryMapper;

    @Autowired
    private InboundDetailMapper inboundDetailMapper;

    @Override
    public void update(InboundDetail inboundDetail) {
        inboundDetailMapper.update(inboundDetail);

        InboundSummary inboundSummary = inboundSummaryMapper.findById(inboundDetail.getSummaryId());
        inboundSummary.setUpdateTime(LocalDateTime.now());

        inboundSummaryMapper.update(inboundSummary);
    }

    @Override
    public void delete(Integer id) {
        InboundSummary inboundSummary = inboundSummaryMapper.findByDetailId(id);
        inboundDetailMapper.delete(id);

        inboundSummary.setUpdateTime(LocalDateTime.now());
        inboundSummaryMapper.update(inboundSummary);
    }
}
