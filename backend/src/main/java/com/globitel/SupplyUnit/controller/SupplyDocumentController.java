package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.constant.DocumentStatus;
import com.globitel.SupplyUnit.model.dto.SupplyDocumentDto;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import com.globitel.SupplyUnit.service.SupplyDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin({"http://127.0.0.1:4200", "http://localhost:4200"})
@RequestMapping("/api/v1/supplyDocument")
public class SupplyDocumentController {

    private final SupplyDocumentService supplyDocumentService;  // Service to handle business logic

    // Endpoint to get supply documents by username
    @GetMapping("")
    public ResponseEntity<List<SupplyDocumentDto>> getSupplyDocumentByUsername(@RequestHeader("Authorization") String authorizationHeader) {
        List<SupplyDocumentDto> supplyDocuments = supplyDocumentService.getSupplyDocumentsByUsername(authorizationHeader);
        return ResponseEntity.ok(supplyDocuments);
    }

    // Endpoint to delete multiple selected supply documents by their IDs
    @PostMapping("/deleteSelected")
    public ResponseEntity<Void> deleteSelectedSupplyDocuments(@RequestBody List<Long> ids) {
        System.out.println('f');

        supplyDocumentService.deleteSelectedSupplyDocuments(ids);
        return ResponseEntity.noContent().build();  // Return 204 No Content
    }

    // Endpoint to add a new supply document
    @PostMapping("")
    public ResponseEntity<Void> addSupplyDocument(@RequestBody SupplyDocumentDto supplyDocumentDto,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
        supplyDocumentService.createSupplyDocument(authorizationHeader, supplyDocumentDto);
        return ResponseEntity.ok().build();  // Return 200 OK
    }

    // Endpoint for managers to retrieve supply documents
    @GetMapping("/manager")
    public ResponseEntity<List<SupplyDocumentDto>> getSupplyDocuments(@RequestHeader("Authorization") String authorizationHeader) {
        List<SupplyDocumentDto> dtoList = supplyDocumentService.findSupplyDocumentsByManagerUsername(authorizationHeader);
        return ResponseEntity.ok(dtoList);  // Return list of supply documents as DTOs
    }

    // Endpoint to update the status of a supply document
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateDocumentStatus(@RequestBody Map<String, Object> requestBody) {
        Long id = Long.valueOf(requestBody.get("id").toString());  // Get document ID from request body
        DocumentStatus status = DocumentStatus.valueOf(requestBody.get("status").toString());  // Get new status

        supplyDocumentService.updateDocumentStatus(id, status);
        return ResponseEntity.ok().build();  // Return 200 OK
    }
}

