package com.piquet.mapper;

import com.piquet.pojo.InboundSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InboundSummaryMapper {

    @Insert("INSERT INTO t_inbound_summary(inbound_code, supplier_id, operator, create_time, update_time, remarks) "+
            "VALUES (#{inboundCode}, #{supplierId}, #{operator}, #{createTime}, #{updateTime}, #{remarks})")
    void addSummary(InboundSummary inboundSummary);

    @Select("SELECT id FROM t_inbound_summary WHERE inbound_code=#{inboundCode}")
    Integer findSummaryIdByInboundNum(String inboundCode);

    @Delete("DELETE FROM t_inbound_summary WHERE id=#{id}")
    void deleteSummary(Integer id);

    @Select("SELECT * FROM t_inbound_summary WHERE id=#{id}")
    InboundSummary findById(Integer id);

    List<InboundSummary> list(String inboundCode, Integer supplierId, String startDate, String endDate);

    @Update("UPDATE t_inbound_summary SET " +
            "supplier_id=#{supplierId}, update_time=#{updateTime}, remarks=#{remarks} WHERE id=#{id}")
    void update(InboundSummary inboundSummary);

    @Select("SELECT inbound_code FROM t_inbound_summary ORDER BY id DESC LIMIT 1")
    String findFinalCode();

    @Select("SELECT * FROM t_inbound_summary WHERE id=(SELECT summary_id FROM t_inbound_detail WHERE id=#{id})")
    InboundSummary findByDetailId(Integer id);
}
