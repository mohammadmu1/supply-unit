package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDocumentRepository extends JpaRepository<SupplyDocument, Long> {


    @Procedure(procedureName = "GetSupplyDocumentsByUsername")
    List<SupplyDocument> findSupplyDocumentByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Procedure(procedureName = "delete_supply_documents")
    void deleteSelectedSupplyDocuments(@Param("docIds") String docIds);


    @Procedure(procedureName = "createSupplyDocument")
    void createSupplyDocument(
            @Param("p_createdBy") String createdBy,
            @Param("p_warehouseId") Long warehouseId,
            @Param("p_itemId") Long itemId,
            @Param("p_name") String name,
            @Param("p_subject") String subject
    );

    @Procedure(procedureName = "UpdateDocumentStatus")
    void updateDocumentStatus(Long docId, String newStatus);

    @Transactional
    @Procedure(procedureName = "GetSupplyDocumentsByManagerUsername")
    List<SupplyDocument> findSupplyDocumentsByManagerUsername(@Param("managerUsername") String managerUsername);

}



