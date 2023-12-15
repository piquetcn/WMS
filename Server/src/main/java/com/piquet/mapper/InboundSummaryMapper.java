package com.piquet.mapper;

import com.piquet.pojo.InboundSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InboundSummaryMapper {

    @Insert("INSERT INTO t_inbound_summary(inbound_code, supplier_id, operator, added_time, update_time, remarks) "+
            "VALUES (#{inboundCode}, #{supplierId}, #{operator}, #{addedTime}, #{updateTime}, #{remarks})")
    void addSummary(InboundSummary inboundSummary);

    @Select("SELECT id FROM t_inbound_summary WHERE inbound_code=#{inboundCode}")
    Integer findSummaryIdByInboundNum(String inboundCode);

    @Delete("DELETE FROM t_inbound_summary WHERE id=#{id}")
    void deleteSummary(Integer id);

    List<InboundSummary> list(String inboundCode, Integer supplierId, String startDate, String endDate);

    @Update("UPDATE t_inbound_summary SET " +
            "supplier_name=#{supplierName}, update_time=#{updateTime}, remarks=#{remarks} WHERE id=#{id}")
    void update(InboundSummary inboundSummary);
}
