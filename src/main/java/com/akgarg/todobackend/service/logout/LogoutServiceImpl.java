package com.akgarg.todobackend.service.logout;

import com.akgarg.todobackend.cache.JwtTokenBlackList;
import com.akgarg.todobackend.exception.UserException;
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

    @Override
    public void logout(final Map<String, String> logoutParams) {
        String jwtToken = logoutParams.get(LOGOUT_REQUEST_TOKEN);

        if (jwtToken == null || jwtToken.isBlank()) {
            throw new UserException(INVALID_JWT_TOKEN);
        } else if (this.jwtTokenBlackList.containsToken(jwtToken)) {
            throw new UserException(UNKNOWN_JWT_TOKEN);
        }

        this.jwtTokenBlackList.addToken(jwtToken);
    }

}