package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import com.globitel.SupplyUnit.repository.SupplyDocumentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SupplyDocumentService {



    private final SupplyDocumentRepository getSupplyDocumentRepository;


    public List<SupplyDocument> getWarehousesByUsername(String username) {
        return getSupplyDocumentRepository.findSupplyDocumentByUsername(username);
    }

    @Transactional
    public void deleteSelectedSupplyDocuments(List<Long> documentIds) {
        String docIdsString = documentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        getSupplyDocumentRepository.deleteSelectedSupplyDocuments(docIdsString);
    }


}
