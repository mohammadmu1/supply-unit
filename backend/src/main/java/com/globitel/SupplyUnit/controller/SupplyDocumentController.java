package com.globitel.SupplyUnit.controller;

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
    private JwtService jwtService;


    @GetMapping("")
    public ResponseEntity<List<SupplyDocument>> getSupplyDocumentByUsername(@RequestHeader("Authorization")
                                                                   String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String username = jwtService.getUserName(token);
        List<SupplyDocument> supplyDocuments = supplyDocumentService.getWarehousesByUsername(username);
        return ResponseEntity.ok(supplyDocuments);
    }

    @PostMapping("/deleteSelected")
    public ResponseEntity<Void> deleteSelectedSupplyDocuments(@RequestBody List<Long> ids) {

        supplyDocumentService.deleteSelectedSupplyDocuments(ids);
        return ResponseEntity.noContent().build();
    }

}
