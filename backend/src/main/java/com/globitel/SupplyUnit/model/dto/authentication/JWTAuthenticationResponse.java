package com.globitel.SupplyUnit.model.dto.authentication;

import lombok.Data;

import java.util.List;

@Data
public class JWTAuthenticationResponse {
    private String token;
    private List<String> role;

    private String username;
}