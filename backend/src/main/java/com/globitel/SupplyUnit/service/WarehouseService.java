package com.globitel.SupplyUnit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WarehouseService {




    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;



    public List<Warehouse> getWarehousesByUsername(String authorizationHeader) {
        String username= jwtService.getUserName(authorizationHeader);
        return warehouseRepository.findWarehousesByUsername(username);
    }
    public void deleteWarehouseByName(String name) {
        try {
            warehouseRepository.deleteWarehouseByName(name);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Cannot delete the warehouse '" + name + "' because it has related records in other tables. Please delete or update dependent records first.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while deleting the warehouse: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Transactional
    public Warehouse addWarehouse(Warehouse warehouse, String authorizationHeader) {

        String username = jwtService.getUserName(authorizationHeader);

        User manager = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        warehouse.setCreatedBy(manager);
        warehouse.setCreatedDateTime(LocalDateTime.now());

        if (warehouse.getItems() != null) {
            warehouse.getItems().forEach(item -> item.setWarehouse(warehouse));
        }

        return warehouseRepository.save(warehouse); //todo SP and avoid N+1
    }

    public List<Item> getItemsByWarehouseName(String warehouseName) {
        return warehouseRepository.findItemsByWarehouseName(warehouseName);
    }

    public List<Warehouse> getAllWarehousesWithItems() {
        return warehouseRepository.getAllWarehousesWithItems();
    }

}
