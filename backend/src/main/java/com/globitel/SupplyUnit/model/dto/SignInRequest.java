package com.globitel.SupplyUnit.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInRequest {

    private String userName;

    private String password;
}