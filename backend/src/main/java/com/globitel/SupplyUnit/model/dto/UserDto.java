package com.globitel.SupplyUnit.model.dto;

import com.globitel.SupplyUnit.constant.UserType;
import com.globitel.SupplyUnit.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;


    private String firstName;
    private String middleName;
    private String lastName;


    private String username;


    private String password;



    private UserType userType;



}
