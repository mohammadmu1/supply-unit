package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.service.JwtService;
import com.globitel.SupplyUnit.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@CrossOrigin({"http://127.0.0.1:4200", "http://localhost:4200"})
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private JwtService jwtService;



    @GetMapping("")
    public ResponseEntity<List<Warehouse>> getWarehousesByUsername(@RequestHeader("Authorization")
                                                                       String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String username = jwtService.getUserName(token);
        List<Warehouse> warehouses = warehouseService.getWarehousesByUsername(username);
        return ResponseEntity.ok(warehouses);
    }

    @PostMapping("")
    public ResponseEntity<Warehouse> createWarehouse(
            @RequestBody Warehouse warehouse,
            @RequestHeader("Authorization") String authorizationHeader) {

        Warehouse createdWarehouse = warehouseService.addWarehouse(warehouse, authorizationHeader);
        return ResponseEntity.ok(createdWarehouse);
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteWarehouseByName(@PathVariable String name) {
        warehouseService.deleteWarehouseByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{warehouseName}/items")
    public ResponseEntity<List<Item>> getItemsByWarehouseName(@PathVariable String warehouseName) {
        List<Item> items = warehouseService.getItemsByWarehouseName(warehouseName);
        return ResponseEntity.ok(items);
    }
}


