package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.request.ForgotPasswordEmailRequest;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.UrlUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
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
 * @author Akhilesh Garg
 * @since 19-07-2022
 */
@RestController
@RequestMapping("/api/v1/password")
@AllArgsConstructor
public class PasswordController {

    private final ApplicationLogger logger;
    private final UserService userService;

    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sendForgotPasswordEmail(
            @RequestBody final ForgotPasswordEmailRequest forgotPasswordEmailRequest, final HttpServletRequest request
    ) {
        logger.info(getClass(), "Forgot password request received for: {}", forgotPasswordEmailRequest);
        ValidationUtils.validateForgotPasswordEmailRequest(forgotPasswordEmailRequest);

        final boolean forgotPasswordEmailResponse = this.userService
                .sendForgotPasswordEmail(
                        forgotPasswordEmailRequest.getEmail(),
                        UrlUtils.getUrl(request)
                );

        return ResponseUtils.generateForgotPasswordResponse(forgotPasswordEmailResponse, forgotPasswordEmailRequest.getEmail());
    }

    @PostMapping(value = "reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody final ForgotPasswordRequest forgotPasswordRequest) {
        logger.info(getClass(), "Received resetPassword request for: {}", forgotPasswordRequest.getForgotPasswordToken());

        final boolean isRequestValid = ValidationUtils.validateForgotPasswordRequest(forgotPasswordRequest);
        boolean passwordResetResponse = false;

        if (isRequestValid) {
            passwordResetResponse = this.userService.processForgotPasswordRequest(forgotPasswordRequest);
        }

        return ResponseUtils.generateForgotPasswordCompleteResponse(isRequestValid, passwordResetResponse);
    }

}
