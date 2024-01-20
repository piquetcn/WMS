package com.piquet.mapper;

import com.piquet.pojo.DynamicInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DynamicInventoryMapper {

    @Select("SELECT * FROM v_dynamic_inventory WHERE goods_id=#{goodsId}")
    List<DynamicInventory> findByGoodsId(Integer goodsId);

    @Select("SELECT min_inventory FROM t_goods WHERE id=#{goodsId}")
    Float getMinInventory(Integer goodsId);
}
