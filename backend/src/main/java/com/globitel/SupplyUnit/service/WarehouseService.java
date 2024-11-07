package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.repository.UserRepository;
import com.globitel.SupplyUnit.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class WarehouseService {

    // Service to handle JWT operations
    private final JwtService jwtService;

    // Repository for managing user data
    private final UserRepository userRepository;

    // Repository for managing warehouse data
    private final WarehouseRepository warehouseRepository;

    // Method to retrieve warehouses associated with a specific username
    @Transactional
    public List<Warehouse> getWarehousesByUsername(String authorizationHeader) {
        // Extract username from the JWT token
        String username = jwtService.getUserName(authorizationHeader);

        // Retrieve and return the warehouses for the given username
        return warehouseRepository.findWarehousesByUsername(username);
    }

    // Method to delete a warehouse by its name
    public void deleteWarehouseByName(String name) {
        try {
            // Attempt to delete the warehouse by name
            warehouseRepository.deleteWarehouseByName(name);
        } catch (DataIntegrityViolationException e) {
            // Handle cases where the warehouse cannot be deleted due to related records
            System.err.println("Cannot delete the warehouse '" + name + "' because it has related records in other tables. Please delete or update dependent records first.");
        } catch (Exception e) {
            // Handle any unexpected errors during deletion
            System.err.println("An unexpected error occurred while deleting the warehouse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to add a new warehouse and associate it with a user
    @Transactional
    public Warehouse addWarehouse(Warehouse warehouse, String authorizationHeader) {
        // Extract username from the JWT token
        String username = jwtService.getUserName(authorizationHeader);

        // Find the user by username or throw an error if not found
        User manager = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        // Set the user as the creator and set the creation date for the warehouse
        warehouse.setCreatedBy(manager);
        warehouse.setCreatedDateTime(LocalDateTime.now());

        // If the warehouse has items, associate each item with this warehouse
        if (warehouse.getItems() != null) {
            warehouse.getItems().forEach(item -> item.setWarehouse(warehouse));
        }

        // Save and return the warehouse
        return warehouseRepository.save(warehouse); // TODO: (SP) and avoiding N+1 problem
    }

    // Method to retrieve items in a specific warehouse by its name
    public List<Item> getItemsByWarehouseName(String warehouseName) {
        // Find and return items associated with the specified warehouse name
        return warehouseRepository.findItemsByWarehouseName(warehouseName);
    }

    // Method to retrieve all warehouses with their items (filtering is handled on the frontend)
    @Transactional
    public List<Warehouse> getAllWarehousesWithItems() {
        // Retrieve and return all warehouses along with their items
        return warehouseRepository.getAllWarehousesWithItems();
    }
}
