package com.piquet.mapper;

import com.piquet.pojo.InboundDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InboundDetailMapper {

    @Select("SELECT * FROM t_inbound_detail WHERE summary_id=#{id}")
    List<InboundDetail> findBySummaryId(Integer id);

    @Insert("INSERT INTO t_inbound_detail(summary_id, goods_id, quantity, price, total, warehouse_id, state) "+
            "VALUES (#{summaryId}, #{goodsId}, #{quantity}, #{price}, #{total}, #{warehouseId}, #{state})")
    void addDetail(InboundDetail inboundDetail);

    List<InboundDetail> list(String inboundNum, String supplierName);

    @Delete("DELETE FROM t_inbound_detail WHERE summary_id=#{id}")
    void deleteBySummaryId(Integer id);
}
