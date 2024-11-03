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
    private AuthenticationService authenticationService;

    @PostMapping("/addUser")
    public ResponseEntity<ResponseDto> SignUp(@RequestBody SignUpRequest request){
        authenticationService.saveUser(request);
        return new ResponseEntity<>(new ResponseDto(true, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationResponse> SignIn(@RequestBody SignInRequest request){
        JWTAuthenticationResponse response = authenticationService.signIn(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}