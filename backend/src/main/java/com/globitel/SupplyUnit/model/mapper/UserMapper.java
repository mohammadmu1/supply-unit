package com.globitel.SupplyUnit.model.mapper;

import com.globitel.SupplyUnit.model.dto.UserDto;
import com.globitel.SupplyUnit.model.entity.User;

public class UserMapper {

    public static User toEntity(UserDto dto) {
        String fullName = (dto.getFirstName() != null ? dto.getFirstName() : "") +
                (dto.getMiddleName() != null ? " " + dto.getMiddleName() : "") +
                (dto.getLastName() != null ? " " + dto.getLastName() : "");

        return User.builder()
                .id(dto.getId())
                .fullName(fullName.trim())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userType(dto.getUserType())
                .build();
    }

    public static UserDto fromEntity(User user) {
        String[] names = user.getFullName() != null ? user.getFullName().split(" ") : new String[0];
        String firstName = names.length > 0 ? names[0] : "";
        String middleName = names.length > 2 ? names[1] : "";
        String lastName = names.length > 1 ? names[names.length - 1] : "";

        return UserDto.builder()
                .id(user.getId())
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .username(user.getUsername())
                .password(user.getPassword())
                .userType(user.getUserType())
                .build();
    }
}
