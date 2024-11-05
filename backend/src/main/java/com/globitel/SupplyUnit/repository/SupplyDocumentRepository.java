package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.dto.SupplyDocumentDto;
import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
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


    @Procedure(name = "createSupplyDocument")
    void createSupplyDocument(
            @Param("p_createdBy") String createdBy,
            @Param("p_warehouseId") Long warehouseId,
            @Param("p_itemId") Long itemId,
            @Param("p_name") String name,
            @Param("p_subject") String subject
    );
    @Procedure(name = "UpdateDocumentStatus")
    void updateDocumentStatus(Long docId, String newStatus);

    @Query(value = "CALL GetSupplyDocumentsByManagerUsername(:managerUsername)", nativeQuery = true)
    List<SupplyDocument> findSupplyDocumentsByManagerUsername(@Param("managerUsername") String managerUsername);

}



