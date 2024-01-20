package com.piquet.mapper;

import com.piquet.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Insert("INSERT INTO t_goods(goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks) " +
            "VALUES (#{goodsCode}, #{goodsName}, #{goodsSpec}, #{goodsUnit}, #{categoryId}, #{goodsPic}, #{manufacturer}, #{minInventory}, #{state}, #{remarks})")
    void add(Goods goods);

    @Delete("DELETE FROM t_goods WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_goods SET " +
            "goods_name=#{goodsName}, goods_pic=#{goodsPic}, manufacturer=#{manufacturer}, min_inventory=#{minInventory}, state=#{state}, remarks=#{remarks} WHERE id=#{id}")
    void update(Goods goods);

    List<Goods> list(String goodsCode, String goodsName, Integer categoryId, Boolean state);

    @Select("SELECT goods_code FROM t_goods WHERE category_id=#{categoryId} ORDER BY id DESC LIMIT 1")
    String findFinalCodeByCategoryId(Integer categoryId);

    @Select("SELECT * FROM t_goods")
    List<Goods> findAll();

    @Select("SELECT goods_name FROM t_goods WHERE id=#{id}")
    String findGoodsName(Integer id);
}
