package com.piquet.mapper;

import com.piquet.pojo.OutboundDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundDetailMapper {

    @Insert("INSERT INTO t_outbound_detail (summary_id, goods_id, quantity, warehouse_id, state) " +
            "VALUES (#{summaryId}, #{goodsId}, #{quantity}, #{warehouseId}, #{state})")
    void add(OutboundDetail outboundDetail);

    @Delete("DELETE FROM t_outbound_detail WHERE id=#{id}")
    void delete(Integer id);
}
