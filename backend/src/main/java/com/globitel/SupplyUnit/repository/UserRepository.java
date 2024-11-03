package com.globitel.SupplyUnit.repository;

import com.globitel.SupplyUnit.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "CALL GetAllUsers()", nativeQuery = true)
    List<User> getAllUsers();
}
