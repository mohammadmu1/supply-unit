package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.repository.WarehouseRepository;
import com.globitel.SupplyUnit.service.JwtService;
import com.globitel.SupplyUnit.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@CrossOrigin({"http://127.0.0.1:4200", "http://localhost:4200"})
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseRepository warehouseRepository;



    @GetMapping("")
    public ResponseEntity<List<Warehouse>> getWarehousesByUsername(@RequestHeader("Authorization")
                                                                       String authorizationHeader) {

        List<Warehouse> warehouses = warehouseService.getWarehousesByUsername(authorizationHeader);
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

    @PostMapping("/items") // todo Use SP
    public ResponseEntity<List<Item>> getItemsByWarehouseName(@RequestBody Map<String, String> request) {
        String warehouseName = request.get("warehouseName");
        List<Item> items = warehouseRepository.getWarehouseByName(warehouseName).getItems();
        return ResponseEntity.ok(items);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getAllWarehousesWithItems() {
        List<Warehouse> warehouses = warehouseService.getAllWarehousesWithItems();
        return ResponseEntity.ok(warehouses);
    }
}


