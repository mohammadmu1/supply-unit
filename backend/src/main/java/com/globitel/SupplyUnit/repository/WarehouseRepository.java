package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Procedure(procedureName = "GetWarehousesByUsername")
    List<Warehouse> findWarehousesByUsername(@Param("username") String username);



    @Procedure(procedureName = "deleteWarehouseAndItems")
    void deleteWarehouseByName(@Param("name")String warehouseName);

    @Procedure(procedureName = "GetItemsByWarehouseName")
    List<Item> findItemsByWarehouseName(@Param("warehouseName") String warehouseName);



    @Procedure(procedureName = "getAllWarehousesWithItems")
    List<Warehouse> getAllWarehousesWithItems();


    Warehouse getWarehouseByName(String warehouseName);
}
