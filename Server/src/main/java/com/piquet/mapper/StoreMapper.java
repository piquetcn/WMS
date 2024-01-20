package com.piquet.mapper;

import com.piquet.pojo.Logistics;
import com.piquet.pojo.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Insert("INSERT INTO t_store (store_name, platform_id, state, remarks) " +
            "VALUES (#{storeName}, #{platformId}, #{state}, #{remarks})")
    void add(Store store);

    @Delete("DELETE FROM t_store WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_store SET " +
            "store_name=#{storeName}, platform_id=#{platformId}, state=#{state}, remarks=#{remarks}")
    void update(Store store);

    List<Store> list(String storeName, Integer platformId, Boolean state);

    @Select("SELECT * FROM t_store")
    List<Store> findAll();
}
