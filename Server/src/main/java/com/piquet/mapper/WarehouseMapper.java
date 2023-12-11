package com.piquet.mapper;

import com.piquet.pojo.Warehouse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarehouseMapper {

    @Insert("INSERT INTO t_warehouse(warehouse_num, warehouse_name, warehouse_size, warehouse_location, state, remarks) " +
            "VALUES (#{warehouseNum}, #{warehouseName}, #{warehouseSize}, #{warehouseLocation}, #{state}, #{remarks})")
    void add(Warehouse warehouse);

    @Delete("DELETE FROM t_warehouse WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_warehouse SET " +
            "warehouse_num=#{warehouseNum}, warehouse_name=#{warehouseName}, warehouse_size=#{warehouseSize}, warehouse_location=#{warehouseLocation}, state=#{state}, remarks=#{remarks}")
    void update(Warehouse warehouse);

    List<Warehouse> list(String warehouseNum, String warehouseName, Boolean state);
}
