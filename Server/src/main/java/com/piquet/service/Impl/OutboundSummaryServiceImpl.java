package com.piquet.service.Impl;

import com.piquet.mapper.InvoiceSummaryMapper;
import com.piquet.mapper.OutboundSummaryMapper;
import com.piquet.pojo.OutboundSummary;
import com.piquet.service.InvoiceSummaryService;
import com.piquet.service.OutboundSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OutboundSummaryServiceImpl implements OutboundSummaryService {

    @Autowired
    private OutboundSummaryMapper outboundSummaryMapper;

    @Autowired
    private InvoiceSummaryService invoiceSummaryService;

    public Map<String, Object> create() {

        Integer maxRetries = 5;
        Integer currentRetry = 0;
        Boolean success = false;

        Map<String, Object> map = new HashMap<>();

        while (currentRetry < maxRetries && !success) {
            OutboundSummary outboundSummary = new OutboundSummary();

            try {
                String outboundCode = getCode();
                outboundSummary.setOutboundCode(outboundCode);
                outboundSummary.setOperator(0);
                outboundSummary.setExecute(false);
                outboundSummary.setOutbound(false);
                outboundSummary.setState(true);

                LocalDateTime currentTime = LocalDateTime.now();
                outboundSummary.setCreateTime(currentTime);

                outboundSummaryMapper.add(outboundSummary);

                map.put("outBoundCode", outboundCode);
                map.put("createTime", currentTime);
                map.put("id", outboundSummaryMapper.getIdByOutboundCode(outboundCode));

                success = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());

                currentRetry++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
        return map;
    }

    @Override
    public void addRemark(Integer id,String remark) {
        outboundSummaryMapper.addRemark(id, remark);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {

        Integer id = invoiceSummaryService.matchOutboundIdByOrderId(orderId);

        outboundSummaryMapper.delete(id);
    }

    @Override
    public boolean getExecuteByOrderId(Integer orderId) {
        Integer id = invoiceSummaryService.matchOutboundIdByOrderId(orderId);
        return outboundSummaryMapper.getExecute(id);
    }

    @Override
    public boolean getOutboundByOrderId(Integer orderId) {
        Integer id = invoiceSummaryService.matchOutboundIdByOrderId(orderId);
        return outboundSummaryMapper.getOutbound(id);
    }

    public String getCode() {
        String code = outboundSummaryMapper.findFinalCode();
        Integer num = Integer.parseInt(code);
        num += 1;
        code = String.format("%08d", num);

        return code;
    }
}
