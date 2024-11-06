package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.constant.DocumentStatus;
import com.globitel.SupplyUnit.model.dto.SupplyDocumentDto;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.repository.SupplyDocumentRepository;
import com.globitel.SupplyUnit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SupplyDocumentService {

    // Repository for managing supply documents
    private final SupplyDocumentRepository getSupplyDocumentRepository;

    // Repository for managing user data
    private final UserRepository userRepository;

    // Repository for creating and updating supply documents
    private final SupplyDocumentRepository supplyDocumentRepository;

    // Service for handling JWT operations
    private final JwtService jwtService;

    // Method to get supply documents associated with a specific username
    @Transactional
    public List<SupplyDocument> getSupplyDocumentsByUsername(String authorizationHeader) {
        // Extract username from the JWT token
        String username = jwtService.getUserName(authorizationHeader);

        // Retrieve user by username or throw an error if not found
        User employee = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        try {
            // Retrieve and return supply documents linked to the username
            return getSupplyDocumentRepository.findSupplyDocumentByUsername(username);
        } catch (Exception e) {
            System.err.println("Error occurred while creating SupplyDocument: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create SupplyDocument due to an error", e);
        }
    }

    // Method to delete selected supply documents by their IDs
    @Transactional
    public void deleteSelectedSupplyDocuments(List<Long> documentIds) {
        // Convert document IDs to a comma-separated string
        String docIdsString = documentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // Call repository to delete documents by ID
        getSupplyDocumentRepository.deleteSelectedSupplyDocuments(docIdsString);
    }

    // Method to create a new supply document
    @Transactional
    public void createSupplyDocument(String authorizationHeader, SupplyDocumentDto supplyDocumentDto) {
        // Extract username from the JWT token
        String username = jwtService.getUserName(authorizationHeader);

        try {
            // Call repository to create a supply document using provided data
            supplyDocumentRepository.createSupplyDocument(
                    username,
                    supplyDocumentDto.getWarehouseId(),
                    supplyDocumentDto.getItemId(),
                    supplyDocumentDto.getName(),
                    supplyDocumentDto.getSubject()
            );
        } catch (Exception e) {
            System.err.println("Error occurred while creating SupplyDocument: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create SupplyDocument due to an error", e);
        }
    }

    // Method to retrieve supply documents for a manager by username
    @Transactional
    public List<SupplyDocumentDto> findSupplyDocumentsByManagerUsername(String authorizationHeader) {
        // Extract manager's username from JWT token
        String managerName = jwtService.getUserName(authorizationHeader);

        // Retrieve manager user or throw an error if not found
        User employee = userRepository.findByUsername(managerName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + managerName));

        // Retrieve all supply documents associated with the manager
        List<SupplyDocument> docs = supplyDocumentRepository.findSupplyDocumentsByManagerUsername(managerName);

        // Convert supply documents to DTOs, filtering only pending documents
        List<SupplyDocumentDto> dtoList = docs.stream()
                .filter(doc -> doc.getStatus() == DocumentStatus.Pending)
                .map(doc -> SupplyDocumentDto.builder()
                        .name(doc.getName())
                        .subject(doc.getSubject())
                        .id(doc.getId())
                        .createdDateTime(doc.getCreatedDateTime())
                        .itemName(doc.getItem().getName())
                        .warehouseName(doc.getWarehouse().getName())
                        .employeeName(doc.getCreatedBy().getFullName())
                        .build())
                .collect(Collectors.toList());

        return dtoList;
    }

    // Method to update the status of a specific document
    @Transactional
    public void updateDocumentStatus(Long documentId, DocumentStatus status) {
        // Update the document's status by its ID
        supplyDocumentRepository.updateDocumentStatus(documentId, status.name());
    }
}



