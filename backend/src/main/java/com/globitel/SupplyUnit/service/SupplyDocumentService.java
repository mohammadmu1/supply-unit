package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.exception.ResourceNotFoundException;
import com.globitel.SupplyUnit.model.dto.SupplyDocumentDto;
import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.repository.ItemRepository;
import com.globitel.SupplyUnit.repository.SupplyDocumentRepository;
import com.globitel.SupplyUnit.repository.UserRepository;
import com.globitel.SupplyUnit.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SupplyDocumentService {



    private final SupplyDocumentRepository getSupplyDocumentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final SupplyDocumentRepository supplyDocumentRepository;
    private final WarehouseRepository warehouseRepository;

    private final JwtService jwtService;


    public List<SupplyDocument> getSupplyDocumentsByUsername(String authorizationHeader) {

        String username = jwtService.getUserName(authorizationHeader);

        User employee = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        return getSupplyDocumentRepository.findSupplyDocumentByUsername(username);
    }

    @Transactional
    public void deleteSelectedSupplyDocuments(List<Long> documentIds) {
        String docIdsString = documentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        getSupplyDocumentRepository.deleteSelectedSupplyDocuments(docIdsString);
    }
    @Transactional  //todo find way to use SP to make this transaction by one query
    public void createSupplyDocument(String authorizationHeader, SupplyDocumentDto supplyDocumentDto) {
        String username = jwtService.getUserName(authorizationHeader);

        User employee = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Warehouse warehouse = warehouseRepository.findById(supplyDocumentDto.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + supplyDocumentDto.getWarehouseId()));

        Item item = itemRepository.findById(supplyDocumentDto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + supplyDocumentDto.getItemId()));

        SupplyDocument supplyDocument = SupplyDocument.builder()
                .createdBy(employee)
                .name(supplyDocumentDto.getName())
                .subject(supplyDocumentDto.getSubject())
                .warehouse(warehouse)
                .item(item)
                .createdDateTime(LocalDateTime.now())
                .build();

        supplyDocumentRepository.save(supplyDocument);
    }






}
