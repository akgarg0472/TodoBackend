package com.akgarg.todobackend.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Service
public class JwtServiceImpl implements JwtService, InitializingBean {

    @Value("${todo.security.jwt.secret}")
    private String secretKey;
    @Value("${todo.security.jwt.token-expiry}")
    private long jwtTokenValidity;
    @Value("${todo.security.jwt.token-issuer}")
    private String jwtIssuer;

    @Override
    public void afterPropertiesSet() {
        this.secretKey = Base64.getEncoder()
                .encodeToString(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String extractUsername(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(final UserDetails userDetails) {
        final var claims = new HashMap<String, Object>();
        return this.createToken(claims, userDetails.getUsername());
    }

    private Date extractExpiration(final String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(
            final String token,
            final Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private String createToken(
            final Map<String, Object> claims,
            final String subject
    ) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(this.jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtTokenValidity))
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(
            final String token,
            final UserDetails userDetails
    ) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
