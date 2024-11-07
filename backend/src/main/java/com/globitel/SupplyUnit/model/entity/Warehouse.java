package com.globitel.SupplyUnit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;  // User (Manager)

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Item> items;

    @PrePersist
    public void prePersist() {
        this.createdDateTime = LocalDateTime.now();
    }


}
