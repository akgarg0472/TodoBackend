package com.akgarg.todobackend.service.logout;

import com.akgarg.todobackend.cache.JwtTokenBlackList;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.ApplicationLogger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 10-09-2022
 */
@Service
@AllArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final JwtTokenBlackList jwtTokenBlackList;
    private final ApplicationLogger logger;

    @Override
    public void doLogout(final Map<String, String> logoutParams) {
        this.logger.info(getClass(), "Logging out: {}", logoutParams);
        final String jwtToken = logoutParams.get(LOGOUT_REQUEST_TOKEN);

        if (jwtToken == null || jwtToken.isBlank()) {
            throw new UserException(INVALID_JWT_TOKEN);
        } else if (this.jwtTokenBlackList.containsToken(jwtToken)) {
            throw new UserException(UNKNOWN_JWT_TOKEN);
        }

        this.jwtTokenBlackList.addToken(jwtToken);
    }

}
