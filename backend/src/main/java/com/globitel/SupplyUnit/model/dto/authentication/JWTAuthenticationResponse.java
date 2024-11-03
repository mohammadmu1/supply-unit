package com.globitel.SupplyUnit.model.dto.authentication;

import lombok.Data;



@Data
public class JWTAuthenticationResponse {
    private Long id;
    private String token;
    private String role;
    private String userName;
}