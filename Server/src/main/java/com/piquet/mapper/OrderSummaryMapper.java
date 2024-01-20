package com.piquet.mapper;

import com.piquet.pojo.OrderSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderSummaryMapper {

    @Insert("INSERT INTO t_order_summary (store_id, order_code, recipient_name, recipient_phone, recipient_address, remarks, create_time, payment_time, update_time, state) " +
            "VALUES (#{storeId}, #{orderCode}, #{recipientName}, #{recipientPhone}, #{recipientAddress}, #{remarks}, #{createTime}, #{paymentTime}, #{updateTime}, #{state})")
    void add(OrderSummary orderSummary);

    @Select("SELECT id FROM t_order_summary WHERE order_code=#{orderCode}")
    Integer findIdByOrderCode(String orderCode);

    @Delete("DELETE FROM t_order_summary WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_order_summary SET " +
            "recipient_name=#{recipientName}, recipient_phone=#{recipientPhone}, recipient_address=#{recipientAddress}, remarks=#{remarks}, create_time=#{createTime}, payment_time=#{paymentTime}, update_time=#{updateTime}, state=#{state}")
    void update(OrderSummary orderSummary);

    List<OrderSummary> list(Integer pageNum, Integer pageSize, String orderCode, String recipientName, String recipientPhone, String recipientAddress, String startCreateTime, String endCreateTime, String startPaymentTime, String endPaymentTime, String startUpdateTime, String endUpdateTime, Boolean state);
}
