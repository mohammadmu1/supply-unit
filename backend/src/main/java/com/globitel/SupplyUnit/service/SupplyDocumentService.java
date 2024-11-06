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


    private final SupplyDocumentRepository getSupplyDocumentRepository;
    private final UserRepository userRepository;
    private final SupplyDocumentRepository supplyDocumentRepository;

    private final JwtService jwtService;



    @Transactional
    public List<SupplyDocument> getSupplyDocumentsByUsername(String authorizationHeader) {

        String username = jwtService.getUserName(authorizationHeader);

        User employee = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        try {
            return getSupplyDocumentRepository.findSupplyDocumentByUsername(username);
        } catch (Exception e) {
            System.err.println("Error occurred while creating SupplyDocument: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create SupplyDocument due to an error", e);
        }
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
            e.printStackTrace();
            throw new RuntimeException("Failed to create SupplyDocument due to an error", e);
        }
    }

    @Transactional
    public List<SupplyDocumentDto> findSupplyDocumentsByManagerUsername(String authorizationHeader) {
        String managerName = jwtService.getUserName(authorizationHeader);

        User employee = userRepository.findByUsername(managerName)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + managerName));
        List<SupplyDocument> docs = supplyDocumentRepository.findSupplyDocumentsByManagerUsername(managerName);

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

    @Transactional
    public void updateDocumentStatus(Long documentId, DocumentStatus status) {
        supplyDocumentRepository.updateDocumentStatus(documentId, status.name());
    }

}




