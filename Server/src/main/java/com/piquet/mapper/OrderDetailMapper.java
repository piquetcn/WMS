package com.piquet.mapper;

import com.piquet.pojo.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {

    @Insert("INSERT INTO t_order_detail (order_id, goods_id, quantity, price, total, state) " +
            "VALUES (orderId, goodsId, quantity, price, total, state)")
    void add(OrderDetail orderDetail);

    @Delete("DELETE FROM t_order_detail WHERE order_id=#{orderId}")
    void deleteByOrderId(Integer orderId);
}
