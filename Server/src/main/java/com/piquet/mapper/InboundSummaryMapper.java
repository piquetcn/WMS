package com.piquet.mapper;

import com.piquet.pojo.InboundSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InboundSummaryMapper {

    @Insert("INSERT INTO t_inbound_summary(inbound_num, supplier_name, operator, added_time, update_time, remarks) "+
            "VALUES (#{inboundNum}, #{supplierName}, #{operator}, #{addedTime}, #{updateTime}, #{remarks})")
    void addSummary(InboundSummary inboundSummary);

    @Select("SELECT id FROM t_inbound_summary WHERE inbound_num=#{inboundNum}")
    Integer findSummaryIdByInboundNum(String inboundNum);

    @Delete("DELETE FROM t_inbound_summary WHERE id=#{id}")
    void deleteSummary(Integer id);

    List<InboundSummary> list(String inboundNum, String supplierName, String startDate, String endDate);

    @Update("UPDATE t_inbound_summary SET " +
            "supplier_name=#{supplierName}, update_time=#{updateTime}, remarks=#{remarks} WHERE id=#{id}")
    void update(InboundSummary inboundSummary);
}
