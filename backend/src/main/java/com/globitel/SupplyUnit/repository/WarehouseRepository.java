package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value = "CALL GetWarehousesByUsername(:username)", nativeQuery = true)
    List<Warehouse> findWarehousesByUsername(@Param("username") String username);

    @Procedure(procedureName = "deleteWarehouseAndItems")
    void deleteWarehouseByName(String name);

    @Procedure(procedureName = "GetItemsByWarehouseName")
    List<Item> findItemsByWarehouseName(@Param("warehouseName") String warehouseName);
    }
