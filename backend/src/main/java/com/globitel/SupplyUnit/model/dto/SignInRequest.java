package com.globitel.SupplyUnit.model.dto.authentication;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInRequest {

    private String email;

    private String password;
}