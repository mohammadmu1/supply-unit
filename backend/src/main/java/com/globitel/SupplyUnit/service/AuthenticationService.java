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

    // Repository for managing user data in the database
    private UserRepository userRepository;

    // Manager for handling authentication processes
    private final AuthenticationManager authenticationManager;

    // Service to generate and validate JWT tokens
    private final JwtService jwtService;

    // Encoder to securely hash passwords
    private final PasswordEncoder passwordEncoder;

    // Method to register and save a new user in the database
    public void saveUser(SignUpRequest signupRequest) {
        // Create a new user entity and set its details from the signup request
        User user = new User();
        user.setFullName(signupRequest.getName());
        user.setUsername(signupRequest.getUserName());

        // Encode and set the user's password for security
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Set the user's role and save the user to the repository
        user.setRole(signupRequest.getRole());
        userRepository.save(user);
    }

    // Method to handle user sign-in and JWT token generation
    public JWTAuthenticationResponse signIn(SignInRequest signinRequest) {
        try {
            // Authenticate the user with username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUserName(), signinRequest.getPassword())
            );
        } catch (Exception e) {
            // If authentication fails, throw an exception indicating user not found
            throw new ResourceNotFoundException("User not found");
        }

        // Retrieve the user from the repository by username
        User user = userRepository.findByUsername(signinRequest.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate a JWT token for the authenticated user
        String token = jwtService.generateToken(user);

        // Create a response with the generated token and user details
        JWTAuthenticationResponse authenticationResponse = new JWTAuthenticationResponse();
        authenticationResponse.setToken(token);      // Set the token in the response
        authenticationResponse.setId(user.getId());  // Set the user ID
        authenticationResponse.setRole(user.getRole().toString());  // Set the user role
        authenticationResponse.setUserName(user.getFullName());     // Set the user's full name

        // Return the authentication response containing the JWT token and user details
        return authenticationResponse;
    }
}
