package com.piquet.mapper;

import com.piquet.pojo.Warehouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseMapper {

    @Insert("INSERT INTO t_warehouse(warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks) " +
            "VALUES (#{warehouseCode}, #{warehouseName}, #{warehouseSize}, #{warehousePic}, #{warehouseLocation}, #{state}, #{remarks})")
    void add(Warehouse warehouse);

    @Delete("DELETE FROM t_warehouse WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_warehouse SET " +
            "warehouse_name=#{warehouseName}, warehouse_size=#{warehouseSize}, warehouse_pic=#{warehousePic}, warehouse_location=#{warehouseLocation}, state=#{state}, remarks=#{remarks} WHERE id=#{id}")
    void update(Warehouse warehouse);

    List<Warehouse> list(String warehouseCode, String warehouseName, Boolean state);

    @Select("SELECT * FROM t_warehouse")
    List<Warehouse> findAll();
}
