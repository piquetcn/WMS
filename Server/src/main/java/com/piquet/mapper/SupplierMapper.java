package com.piquet.mapper;

import com.piquet.pojo.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierMapper {

    @Result
    @Insert("INSERT INTO t_supplier(supplier_name, supplier_addr, contacts, phone, remarks) " +
            "VALUES(#{supplierName}, #{supplierAddr}, #{contacts}, #{phone}, #{remarks})")
    void add(Supplier supplier);

    @Delete("DELETE FROM t_supplier WHERE id=#{id}")
    void delete(Integer id);

    @Update("UPDATE t_supplier set " +
            "supplier_name=#{supplierName}, supplier_addr=#{supplierAddr}, contacts=#{contacts}, phone=#{phone}, remarks=#{remarks} WHERE id=#{id}")
    void update(Supplier supplier);

    List<Supplier> list(String supplierName);

    @Select("SELECT * FROM t_supplier")
    List<Supplier> findAll();
}
