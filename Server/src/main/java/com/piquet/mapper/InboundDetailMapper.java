package com.piquet.mapper;

import com.piquet.pojo.InboundDetail;
import org.apache.ibatis.annotations.*;

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

    @Update("UPDATE t_inbound_detail SET " +
    "goods_id=#{goodsId}, price=#{price}, quantity=#{quantity}, total=#{total}, state=#{state}, warehouse_id=#{warehouseId} WHERE id=#{id}")
    void update(InboundDetail inboundDetail);

    @Delete("DELETE FROM t_inbound_detail WHERE id=#{id}")
    void delete(Integer id);

}
