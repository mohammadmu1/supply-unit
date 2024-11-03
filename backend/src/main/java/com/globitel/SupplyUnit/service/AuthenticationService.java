package com.globitel.SupplyUnit.service;


import com.globitel.SupplyUnit.exception.ResourceNotFoundException;
import com.globitel.SupplyUnit.model.dto.SignInRequest;
import com.globitel.SupplyUnit.model.dto.SignUpRequest;
import com.globitel.SupplyUnit.model.dto.authentication.JWTAuthenticationResponse;
import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(SignUpRequest signupRequest) {

        User user = new User();
        user.setFullName(signupRequest.getName());
        user.setUsername(signupRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encode password
        user.setRole(signupRequest.getRole());
        userRepository.save(user);
    }


    public JWTAuthenticationResponse signIn(SignInRequest signinRequest) {
        try {  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUserName(), signinRequest.getPassword())
        );}
        catch (Exception e) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = userRepository.findByUsername(signinRequest.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        JWTAuthenticationResponse authenticationResponse = new JWTAuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setId(user.getId());
        authenticationResponse.setRole(user.getRole().toString());
        authenticationResponse.setUserName(user.getFullName());
        return authenticationResponse;
    }
}