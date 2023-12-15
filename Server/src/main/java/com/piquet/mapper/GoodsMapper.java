package com.piquet.mapper;

import com.piquet.pojo.Goods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Insert("INSERT INTO t_goods(goods_code, goods_name, goods_spec, goods_unit, goods_category, goods_pic, manufacturer, min_inventory, state, remarks) " +
            "VALUES (#{goodsCode}, #{goodsName}, #{goodsSpec}, #{goodsUnit}, #{goodsCategory}, #{goodsPic}, #{manufacturer}, #{minInventory}, #{state}, #{remarks})")
    void add(Goods goods);

    @Delete("DELETE FROM t_goods WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_goods SET " +
            "goods_code=#{goodsCode}, goods_name=#{goodsName}, goods_spec=#{goodsSpec}, goods_unit=#{goodsUnit}, goods_category=#{goodsCategory}, goods_pic=#{goodsPic}, manufacturer=#{manufacturer}, min_inventory=#{minInventory}, state=#{state}, remarks=#{remarks} WHERE id=#{id}")
    void update(Goods goods);

    List<Goods> list(String goodsCode, String goodsName, Integer categoryId, Boolean state);
}
