package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import com.akgarg.todobackend.service.user.UserService;
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
    public ResponseEntity<?> sendForgotPasswordEmail(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request
    ) {
        logger.info(getClass(), "Forgot password request received for: {}", forgotPasswordRequest);

        UserUtils.checkForgotPasswordRequest(forgotPasswordRequest);

        String email = forgotPasswordRequest.getEmail();
        boolean forgotPasswordEmailResponse = this.userService.sendForgotPasswordEmail(email, UrlUtils.getUrl(request));
        int responseStatus = forgotPasswordEmailResponse ? 200 : 500;

        return ResponseEntity.status(responseStatus).body(UserUtils.generateForgotPasswordResponse(forgotPasswordEmailResponse, email));
    }

}
