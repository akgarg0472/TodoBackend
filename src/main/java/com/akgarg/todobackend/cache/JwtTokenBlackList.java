package com.akgarg.todobackend.cache;

import com.akgarg.todobackend.logger.ApplicationLogger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Akhilesh Garg
 * @since 06-08-2022
 */
@Component
public final class JwtTokenBlackList {

    private final ApplicationLogger logger;
    private final Map<String, LocalDateTime> jwtBlackList;

    public JwtTokenBlackList(final ApplicationLogger logger) {
        this.logger = logger;
        this.jwtBlackList = new HashMap<>();
    }

    public boolean containsToken(final String token) {
        this.logger.info(getClass(), "Contains token: {}", token);

        return this.jwtBlackList.get(token) != null;
    }

    public void addToken(final String token) {
        this.logger.info(getClass(), "Add token: {}", token);

        this.jwtBlackList.put(token, getTokenExpirationTime());
    }

    private LocalDateTime getTokenExpirationTime() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of("Asia/Kolkata"))
                .toLocalDateTime();
    }

}
