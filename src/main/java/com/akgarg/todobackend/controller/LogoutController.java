package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.INVALID_LOGOUT_REQUEST;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 06-08-2022
 */
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class LogoutController {

    private final TodoLogger logger;
    private final UserService userService;

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody(required = false) Map<String, String> logoutRequestBody) {
        this.logger.info(getClass(), "Logout request received for: {}", logoutRequestBody);

        if (logoutRequestBody == null || logoutRequestBody.isEmpty()) {
            throw new UserException(INVALID_LOGOUT_REQUEST);
        }

        this.userService.logout(logoutRequestBody);

        return ResponseEntity.ok(UserUtils.generateLogoutResponse());
    }

}
