package com.globitel.SupplyUnit.service.implemention;

import com.globitel.SupplyUnit.exception.UserNotFoundException;
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



}
