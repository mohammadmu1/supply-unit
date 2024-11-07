package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.service.JwtService;
import com.globitel.SupplyUnit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // Service for handling user-related operations
    final UserService userService;

    // Constructor to initialize UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Service to handle JWT-related operations
    @Autowired
    private JwtService jwtService;

    // Endpoint to extract and return the username from the JWT token in the request header
    @GetMapping("/username")
    public String getUserNameFromToken(@RequestHeader("Authorization") String authorizationHeader) {

        // Remove the "Bearer " prefix to get the actual token
        String token = authorizationHeader.replace("Bearer ", "");

        // Use JwtService to extract the username from the token
        String username = jwtService.getUserName(token);

        // Return the extracted username
        return "Username: " + username;
    }

    // Endpoint to retrieve a list of all users
    @GetMapping("")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
