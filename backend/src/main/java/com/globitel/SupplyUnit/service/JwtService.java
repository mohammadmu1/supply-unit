package com.globitel.SupplyUnit.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key for signing and validating JWT tokens
    private static final String SECRET_KEY = "f3851c262342b45756d0cdf193a87805bc8683a1d540c5e686b1df570fa1c990";

    // Method to retrieve the signing key used for HMAC SHA encryption
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);  // Decode the secret key
        return Keys.hmacShaKeyFor(keyBytes);  // Generate the signing key
    }

    // Extract the username from the JWT token in the authorization header
    public String getUserName(String authorizationHeader) {
        // Remove the "Bearer " prefix to get the token
        String token = authorizationHeader.replace("Bearer ", "");

        // Retrieve and return the subject (username) from the token claims
        return getClaim(token, Claims::getSubject);
    }

    // Retrieve all claims from the provided JWT token
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())  // Set the signing key
                .build()
                .parseClaimsJws(token)  // Parse the token
                .getBody();  // Get the claims from the token
    }

    // Generic method to retrieve a specific claim from the JWT token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);  // Get all claims
        return claimsResolver.apply(claims);  // Resolve and return the specific claim
    }

    // Generate a JWT token for a user with custom claims
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", getRoleFromUserDetails(userDetails));  // Add user role to claims
        return generateToken(claims, userDetails);
    }

    // Generate a JWT token with specific claims and user details
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)  // Set the custom claims
                .setSubject(userDetails.getUsername())  // Set the username as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))  // Set expiration to 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)  // Sign with HMAC SHA-256
                .compact();  // Generate and return the token as a string
    }

    // Validate the JWT token by checking username, role, and expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);  // Extract username from token
        final String roleFromToken = getClaims(token).get("role", String.class);  // Extract role from token
        final String roleFromUserDetails = getRoleFromUserDetails(userDetails);  // Get user role

        // Check if username and role match, and token is not expired
        return (username.equals(userDetails.getUsername())
                && roleFromToken.equals(roleFromUserDetails)
                && !isTokenExpired(token));
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());  // Compare expiration date with current date
    }

    // Retrieve the expiration date from the token claims
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Get the user's role from the UserDetails object
    private String getRoleFromUserDetails(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)  // Extract the authority (role)
                .findFirst()
                .orElse(null);  // Return the role or null if not found
    }
}
