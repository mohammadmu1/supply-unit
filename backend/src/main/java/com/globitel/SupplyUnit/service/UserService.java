package com.globitel.SupplyUnit.service;

import com.globitel.SupplyUnit.exception.UserNotFoundException;
import com.globitel.SupplyUnit.model.dto.UserDto;
import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.model.mapper.UserMapper;
import com.globitel.SupplyUnit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return UserMapper.fromEntity(user);
    }

    public UserDto createUser(UserDto userDto) {

        User user = UserMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.fromEntity(savedUser);
    }

}
