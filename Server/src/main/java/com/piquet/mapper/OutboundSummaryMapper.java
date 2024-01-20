package com.piquet.mapper;

import com.piquet.pojo.OutboundSummary;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OutboundSummaryMapper {

    @Select("SELECT outbound_code FROM t_outbound_summary ORDER BY id DESC LIMIT 1")
    String findFinalCode();

    @Insert("INSERT INTO t_outbound_summary (outbound_code, create_time, execute, outbound, state) " +
            "VALUES (#{outboundCode}, #{createTime}, #{execute}, #{outbound}, #{state})")
    void add(OutboundSummary outboundSummary);

    @Select("SELECT id FROM t_outbound_summary WHERE outbound_code=#{outboundCode}")
    Object getIdByOutboundCode(String outboundCode);

    @Update("UPDATE t_outbound_summary SET remarks=CONCAT(remarks, #{remark}) WHERE id=#{id}")
    void addRemark(Integer id, String remark);

    @Delete("DELETE FROM t_outbound_summary WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT execute FROM t_outbound_summary WHERE id=#{id}")
    boolean getExecute(Integer id);

    @Select("SELECT outbound FROM t_outbound_summary WHERE id=#{id}")
    boolean getOutbound(Integer id);
}
