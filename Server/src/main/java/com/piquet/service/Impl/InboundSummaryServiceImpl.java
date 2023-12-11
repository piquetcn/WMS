package com.piquet.service.Impl;

import com.piquet.mapper.InboundDetailMapper;
import com.piquet.mapper.InboundSummaryMapper;
import com.piquet.pojo.InboundDetail;
import com.piquet.pojo.InboundSummary;
import com.piquet.pojo.PageBean;
import com.piquet.service.InboundSummaryService;
import com.piquet.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class InboundSummaryServiceImpl implements InboundSummaryService {

    @Autowired
    private InboundSummaryMapper inboundSummaryMapper;

    @Autowired
    private InboundDetailMapper inboundDetailMapper;

    @Override
    public void add(InboundSummary inboundSummary) {
        inboundSummary.setAddedTime(LocalDateTime.now());
        inboundSummary.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        String nickname = (String) map.get("nickname");

        inboundSummary.setOperator(nickname);
        inboundSummaryMapper.addSummary(inboundSummary);

        if (! inboundSummary.getInboundDetailList().isEmpty()) {

            Integer sid = inboundSummaryMapper.findSummaryIdByInboundNum(inboundSummary.getInboundNum());

            for (InboundDetail inboundDetail:
                 inboundSummary.getInboundDetailList()) {
                inboundDetail.setSummaryId(sid);
                inboundDetailMapper.addDetail(inboundDetail);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        inboundSummaryMapper.deleteSummary(id);
        inboundDetailMapper.deleteBySummaryId(id);
    }

    @Override
    public PageBean<InboundSummary> list(Integer pageNum, Integer pageSize, String inboundNum, String supplierName, String startDate, String endDate) {
        List<InboundSummary> isl = inboundSummaryMapper.list(inboundNum, supplierName, startDate, endDate);

        Integer startIdx = (pageNum - 1) * pageSize;
        Integer endIdx = Math.min(startIdx + pageSize, isl.size());

        List<InboundSummary> pagedList = isl.subList(startIdx, endIdx);

        PageBean<InboundSummary> pb = new PageBean<>();
        Long total = Long.valueOf(isl.size());
        pb.setTotal(total);
        pb.setItems(pagedList);

        return pb;
    }

    @Override
    public void update(InboundSummary inboundSummary) {
        inboundSummary.setUpdateTime(LocalDateTime.now());

        inboundSummaryMapper.update(inboundSummary);
        inboundDetailMapper.deleteBySummaryId(inboundSummary.getId());

        for (InboundDetail inboundDetail:
             inboundSummary.getInboundDetailList()) {
            inboundDetail.setSummaryId(inboundSummary.getId());
            inboundDetailMapper.addDetail(inboundDetail);
        }
    }
}
