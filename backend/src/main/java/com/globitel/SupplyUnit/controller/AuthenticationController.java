package com.globitel.SupplyUnit.controller;

import com.globitel.SupplyUnit.model.dto.ResponseDto;
import com.globitel.SupplyUnit.model.dto.SignInRequest;
import com.globitel.SupplyUnit.model.dto.SignUpRequest;
import com.globitel.SupplyUnit.model.dto.authentication.JWTAuthenticationResponse;
import com.globitel.SupplyUnit.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin({"http://127.0.0.1:4200", "http://localhost:4200"})
public class AuthenticationController {
    // Service to handle authentication-related logic
    private AuthenticationService authenticationService;

    // Endpoint for user registration
    @PostMapping("/addUser")
    public ResponseEntity<ResponseDto> SignUp(@RequestBody SignUpRequest request) {
        // Calls the service to save the new user using the provided sign-up request data
        authenticationService.saveUser(request);

        // Returns a response with success status and HTTP 200 OK status
        return new ResponseEntity<>(new ResponseDto(true, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationResponse> SignIn(@RequestBody SignInRequest request) {
        // Calls the service to authenticate the user with the provided login request data
        JWTAuthenticationResponse response = authenticationService.signIn(request);

        // Returns the JWT authentication response with HTTP 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}