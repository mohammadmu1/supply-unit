package com.globitel.SupplyUnit.filter;

import com.globitel.SupplyUnit.model.entity.User;
import com.globitel.SupplyUnit.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Registers the filter as a Spring bean
public class JwtRequestFilter extends OncePerRequestFilter {

    // Dependencies for JWT handling and user details
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor to inject dependencies
    public JwtRequestFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Retrieve the Authorization header from the request
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // Check if the header is null or doesn't start with "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // If no valid header is present, continue with the filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT from the Authorization header
        jwt = authorizationHeader.substring(7);
        // Get the username from the JWT
        userName = jwtService.getUserName(jwt);

        // Check if the username is not null and the user is not already authenticated
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the UserDetailsService
            User userDetails = (User) this.userDetailsService.loadUserByUsername(userName);

            // Validate the JWT token using the user details
            if (jwtService.validateToken(jwt, userDetails)) {
                // Create an authentication token for the user
                UsernamePasswordAuthenticationToken AuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set additional details for the authentication token
                AuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the security context
                SecurityContextHolder.getContext().setAuthentication(AuthenticationToken);
            }
        }

        // Continue with the filter chain after processing the JWT
        filterChain.doFilter(request, response);
    }
}
