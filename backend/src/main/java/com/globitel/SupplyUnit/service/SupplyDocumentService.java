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
      @Transactional
      public void createSupplyDocument(String authorizationHeader, SupplyDocumentDto supplyDocumentDto) {
          String username = jwtService.getUserName(authorizationHeader);

          try {
              supplyDocumentRepository.createSupplyDocument(
                      username,
                      supplyDocumentDto.getWarehouseId(),
                      supplyDocumentDto.getItemId(),
                      supplyDocumentDto.getName(),
                      supplyDocumentDto.getSubject()
              );
          } catch (Exception e) {
              System.err.println("Error occurred while creating SupplyDocument: " + e.getMessage());
              e.printStackTrace(); // Optional: prints the full stack trace for debugging purposes
              throw new RuntimeException("Failed to create SupplyDocument due to an error", e);
          }
      }






}