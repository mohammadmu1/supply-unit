package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.SupplyDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDocumentRepository extends JpaRepository <SupplyDocument, Long> {

    @Query(value = "CALL GetSupplyDocumentsByUsername(:username)", nativeQuery = true)
    List<SupplyDocument> findSupplyDocumentByUsername(@Param("username") String username);

    @Query(value = "CALL DeleteSelectedSupplyDocuments(:doc_ids)", nativeQuery = true)
    void deleteSelectedSupplyDocuments(@Param("doc_ids") String documentIds);

}
