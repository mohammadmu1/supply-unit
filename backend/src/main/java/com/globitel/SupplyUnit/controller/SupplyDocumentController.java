package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.dto.SupplyDocumentDto;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import com.globitel.SupplyUnit.service.JwtService;
import com.globitel.SupplyUnit.service.SupplyDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin({"http://127.0.0.1:4200", "http://localhost:4200"})
@RequestMapping("/api/v1/supplyDocument")
public class SupplyDocumentController {

    private final SupplyDocumentService supplyDocumentService;


    @GetMapping("")
    public ResponseEntity<List<SupplyDocument>> getSupplyDocumentByUsername(@RequestHeader("Authorization")
                                                                            String authorizationHeader) {
        List<SupplyDocument> supplyDocuments = supplyDocumentService.getSupplyDocumentsByUsername(authorizationHeader);
        return ResponseEntity.ok(supplyDocuments);
    }

    @PostMapping("/deleteSelected")
    public ResponseEntity<Void> deleteSelectedSupplyDocuments(@RequestBody List<Long> ids) {

        supplyDocumentService.deleteSelectedSupplyDocuments(ids);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("")
    public ResponseEntity<Void> addSupplyDocument(@RequestBody SupplyDocumentDto supplyDocumentDto,
                                                  @RequestHeader("Authorization")
                                                  String authorizationHeader) {
        supplyDocumentService.createSupplyDocument(authorizationHeader,supplyDocumentDto);
        return ResponseEntity.ok().build();
    }



}