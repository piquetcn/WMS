package com.piquet.service.Impl;

import com.piquet.mapper.GoodsMapper;
import com.piquet.mapper.InvoiceSummaryMapper;
import com.piquet.mapper.OutboundDetailMapper;
import com.piquet.pojo.OrderDetail;
import com.piquet.pojo.OutboundDetail;
import com.piquet.service.DynamicInventoryService;
import com.piquet.service.InvoiceSummaryService;
import com.piquet.service.OutboundDetailService;
import com.piquet.service.OutboundSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OutboundDetailServiceImpl implements OutboundDetailService {

    @Autowired
    private OutboundDetailMapper outboundDetailMapper;

    @Autowired
    private DynamicInventoryService dynamicInventoryService;

    @Autowired
    private OutboundSummaryService outboundSummaryService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private InvoiceSummaryService invoiceSummaryService;

    @Override
    public void add(Integer outboundId, OrderDetail orderDetail) {

        OutboundDetail outboundDetail = new OutboundDetail();
        outboundDetail.setSummaryId(outboundId);

        outboundDetail.setGoodsId(orderDetail.getGoodsId());
        outboundDetail.setQuantity(orderDetail.getQuantity());

        try {
            outboundDetail.setWarehouseId(dynamicInventoryService.matchWarehouse(orderDetail.getGoodsId(), orderDetail.getQuantity()));
        } catch (Exception e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            outboundSummaryService.addRemark(outboundId, goodsMapper.findGoodsName(orderDetail.getGoodsId()) + " 库存不足。 //" + LocalDateTime.now().format(formatter) + "\n");

            outboundDetail.setWarehouseId(0);
        }
        outboundDetail.setState(true);
//        System.out.println(outboundDetail);

        outboundDetailMapper.add(outboundDetail);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        Integer id = invoiceSummaryService.matchOutboundIdByOrderId(orderId);
        outboundDetailMapper.delete(id);
    }
}
