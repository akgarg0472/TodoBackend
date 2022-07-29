package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.request.ForgotPasswordEmailRequest;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.PasswordUtils;
import com.akgarg.todobackend.utils.UrlUtils;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
@RestController
@RequestMapping("/api/v1/password")
@AllArgsConstructor
public class PasswordController {

    private final TodoLogger logger;
    private final UserService userService;

    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sendForgotPasswordEmail(
            @RequestBody ForgotPasswordEmailRequest forgotPasswordEmailRequest, HttpServletRequest request
    ) {
        logger.info(getClass(), "Forgot password request received for: {}", forgotPasswordEmailRequest);
        UserUtils.checkForgotPasswordRequest(forgotPasswordEmailRequest);
        final String email = forgotPasswordEmailRequest.getEmail();
        final boolean forgotPasswordEmailResponse = this.userService.sendForgotPasswordEmail(email, UrlUtils.getUrl(request));

        return UserUtils.generateForgotPasswordResponse(forgotPasswordEmailResponse, forgotPasswordEmailRequest.getEmail());
    }

    @PostMapping(value = "reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        logger.info(getClass(), "Received resetPassword request for: {}", forgotPasswordRequest.getForgotPasswordToken());
        final boolean isRequestValid = PasswordUtils.isForgotPasswordRequestValid(forgotPasswordRequest);
        boolean passwordResetResponse = false;

        if (isRequestValid) {
            passwordResetResponse = this.userService.processForgotPasswordRequest(forgotPasswordRequest);
        }

        return UserUtils.generateForgotPasswordCompleteResponse(isRequestValid, passwordResetResponse);
    }

}
