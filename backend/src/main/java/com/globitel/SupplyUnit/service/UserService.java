package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // Repository for managing user data in the database
    private final UserRepository userRepository;

    // Constructor to initialize the UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to retrieve a list of all users
    public List<User> getAllUsers() {
        // Calls the repository to fetch all user records
        return userRepository.findAll();
    }
}
