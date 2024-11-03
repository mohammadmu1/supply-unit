package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.service.JwtService;
import com.globitel.SupplyUnit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }





    @Autowired
    private JwtService jwtService;

    @GetMapping("/username")
    public String getUserNameFromToken(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");


        String username = jwtService.getUserName(token);

        return "Username: " + username;
    }
    @GetMapping("")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
