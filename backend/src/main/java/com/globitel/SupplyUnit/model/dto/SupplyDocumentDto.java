package com.globitel.SupplyUnit.model.dto;

import com.globitel.SupplyUnit.model.entity.Item;
import com.globitel.SupplyUnit.model.entity.Warehouse;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplyDocumentDto {


    private Long id;
    private Long warehouseId;
    private Long itemId;
    private Long userId;
    private String name;

    private String subject;

    private String itemName;
    private String warehouseName;
    private String employeeName;


    private LocalDateTime createdDateTime;




    @PrePersist
    public void prePersist() {
        this.createdDateTime = LocalDateTime.now();
    }

}
