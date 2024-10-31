package com.globitel.SupplyUnit.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply_documents")
public class SupplyDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;  // Link to the employee who created the document

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;  // Warehouse related to the supply document

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;  // Item being supplied in this document


}
