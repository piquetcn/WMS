package com.piquet.mapper;

import com.piquet.pojo.Logistics;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsMapper {

    @Insert("INSERT INTO t_logistics (logistics_name, contacts, phone, address, state, remarks) " +
            "VALUES (#{logisticsName}, #{contacts}, #{phone}, #{address}, #{state}, #{remarks})")
    void add(Logistics logistics);

    @Delete("DELETE FROM t_logistics WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_logistics SET " +
            "logistics_name=#{logisticsName}, contacts=#{contacts}, phone=#{phone}, address=#{address}, state=#{state}, remarks=#{remarks} WHERE id=#{id}")
    void update(Logistics logistics);

    List<Logistics> list(String logisticsName, Boolean state);

    @Select("SELECT * FROM t_logistics")
    List<Logistics> findAll();
}
