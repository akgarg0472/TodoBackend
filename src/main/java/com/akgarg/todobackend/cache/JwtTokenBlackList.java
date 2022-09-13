package com.akgarg.todobackend.cache;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 06-08-2022
 */
@Component
public class JwtTokenBlackList {

    private final Map<String, LocalDateTime> jwtBlackList;

    public JwtTokenBlackList() {
        this.jwtBlackList = new HashMap<>();
    }

    public boolean containsToken(String token) {
        return this.jwtBlackList.get(token) != null;
    }

    public void addToken(String token) {
        this.jwtBlackList.put(token, getTokenExpirationTime());
    }

    private LocalDateTime getTokenExpirationTime() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of("Asia/Kolkata"))
                .toLocalDateTime();
    }

}
