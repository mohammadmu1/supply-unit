package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.repository.WarehouseRepository;
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

    // Service to handle business logic related to warehouses
    private final WarehouseService warehouseService;

    // Repository to interact with the warehouse database
    private final WarehouseRepository warehouseRepository;

    // Endpoint to retrieve warehouses associated with the username from the authorization header
    @GetMapping("")
    public ResponseEntity<List<Warehouse>> getWarehousesByUsername(@RequestHeader("Authorization") String authorizationHeader) {
        // Get warehouses by username using the authorization token
        List<Warehouse> warehouses = warehouseService.getWarehousesByUsername(authorizationHeader);

        // Return the list of warehouses with HTTP 200 OK status
        return ResponseEntity.ok(warehouses);
    }

    // Endpoint to create a new warehouse
    @PostMapping("")
    public ResponseEntity<Warehouse> createWarehouse(
            @RequestBody Warehouse warehouse, // Warehouse data from request body
            @RequestHeader("Authorization") String authorizationHeader) { // Authorization header for user validation

        // Create a new warehouse based on the provided data and authorization header
        Warehouse createdWarehouse = warehouseService.addWarehouse(warehouse, authorizationHeader);

        // Return the created warehouse with HTTP 200 OK status
        return ResponseEntity.ok(createdWarehouse);
    }

    // Endpoint to delete a warehouse by its name
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteWarehouseByName(@PathVariable String name) {
        // Call the service to delete a warehouse by its name
        warehouseService.deleteWarehouseByName(name);

        // Return a 204 No Content status to indicate successful deletion
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get items by warehouse name (SP usage is noted as a TODO)
    @PostMapping("/items")
    public ResponseEntity<List<Item>> getItemsByWarehouseName(@RequestBody Map<String, String> request) {
        // Retrieve warehouse name from the request body
        String warehouseName = request.get("warehouseName");

        // Find the warehouse by name and get its list of items
        List<Item> items = warehouseRepository.getWarehouseByName(warehouseName).getItems();

        // Return the list of items with HTTP 200 OK status
        return ResponseEntity.ok(items);
    }

    // Endpoint to retrieve all warehouses with their items
    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getAllWarehousesWithItems() {
        // Get all warehouses including their associated items
        List<Warehouse> warehouses = warehouseService.getAllWarehousesWithItems();

        // Return the list of warehouses with HTTP 200 OK status
        return ResponseEntity.ok(warehouses);
    }
}
