package com.globitel.SupplyUnit.model.dto;

import com.globitel.SupplyUnit.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String name;
    private String userName;
    private String password;
    private Role role;
}
