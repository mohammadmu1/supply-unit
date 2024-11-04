package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
