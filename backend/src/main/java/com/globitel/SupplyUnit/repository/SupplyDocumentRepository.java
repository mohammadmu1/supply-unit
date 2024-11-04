package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDocumentRepository extends JpaRepository <SupplyDocument, Long> {

    @Query(value = "CALL GetSupplyDocumentsByUsername(:username)", nativeQuery = true)
    List<SupplyDocument> findSupplyDocumentByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "CALL delete_supply_documents(:docIds)", nativeQuery = true)
    void deleteSelectedSupplyDocuments(@Param("docIds") String docIds);


    @Modifying
    @Transactional
    @Query(value = "CALL CreateSupplyDocument(:username, :warehouseId, :itemId, :name, :subject)", nativeQuery = true)
    void createSupplyDocument(
            @Param("username") String username,
            @Param("warehouseId") Long warehouseId,
            @Param("itemId") Long itemId,
            @Param("name") String name,
            @Param("subject") String subject
    );
}



