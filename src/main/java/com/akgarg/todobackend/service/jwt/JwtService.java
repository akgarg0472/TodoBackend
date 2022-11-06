package com.akgarg.todobackend.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Akhilesh Garg
 * @since 05-11-2022
 */
public interface JwtService {

    String extractUsername(String jwtToken);

    String generateToken(UserDetails userDetails);

    boolean validateToken(
            String token,
            UserDetails userDetails
    );
}
