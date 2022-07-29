package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;
import com.akgarg.todobackend.response.SignupResponse;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.UrlUtils;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.REGISTRATION_SUCCESS_CONFIRM_ACCOUNT;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final UserService userService;
    private final TodoLogger logger;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> registerUser(
            @RequestBody RegisterUserRequest registerUserRequest, HttpServletRequest httpServletRequest
    ) {
        logger.info(getClass(), "Signup request received: {}", registerUserRequest);
        UserUtils.checkRegisterUserRequest(registerUserRequest);
        final String email = this.userService.addNewUser(registerUserRequest, UrlUtils.getUrl(httpServletRequest));

        return ResponseEntity.status(201)
                .body(UserUtils.generateSignupResponse(
                              REGISTRATION_SUCCESS_CONFIRM_ACCOUNT.replace("$email", email),
                              201
                      )
                );
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        UserUtils.checkLoginRequest(loginRequest);
        logger.info(getClass(), "Login request received: {}", loginRequest.getEmail());
        final String token = this.userService.login(loginRequest);

        return ResponseEntity.ok(UserUtils.generateLoginSuccessRequest(token));
    }

    @GetMapping(value = "/verify/{accountVerificationToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> verifyUserAccount(@PathVariable String accountVerificationToken) {
        boolean isTokenValid = UserUtils.checkForNullOrInvalidToken(accountVerificationToken);
        String verifiedAccountEmail = null;

        if (isTokenValid) {
            verifiedAccountEmail = this.userService.verifyUserAccount(accountVerificationToken);
        }

        return UserUtils.generateAccountVerificationResponse(isTokenValid, verifiedAccountEmail);
    }

}
