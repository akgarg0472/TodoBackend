package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;
import com.akgarg.todobackend.response.SignupResponse;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.UrlUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.REGISTRATION_SUCCESS_CONFIRM_ACCOUNT;
import static com.akgarg.todobackend.constants.FrontendConstants.DEFAULT_FRONTEND_BASE_URL;

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
    private final ApplicationLogger logger;
    private final ApplicationCache cache;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> registerUser(
            @RequestBody RegisterUserRequest registerUserRequest, HttpServletRequest httpServletRequest
    ) {
        logger.info(getClass(), "Signup request received: {}", registerUserRequest);
        ValidationUtils.checkRegisterUserRequest(registerUserRequest);

        final String email = this.userService.addNewUser(registerUserRequest, UrlUtils.getUrl(httpServletRequest));

        return ResponseEntity.status(201)
                .body(ResponseUtils.generateSignupResponse(
                              REGISTRATION_SUCCESS_CONFIRM_ACCOUNT.replace("$email", email),
                              201
                      )
                );
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info(getClass(), "Login request received: {}", loginRequest);
        ValidationUtils.checkLoginRequest(loginRequest);

        final Map<String, String> loginResponse = this.userService.login(loginRequest);

        return ResponseEntity.ok(ResponseUtils.generateLoginSuccessRequest(loginResponse));
    }

    @GetMapping(value = "/verify/{accountVerificationToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyUserAccount(@PathVariable String accountVerificationToken) {
        boolean isTokenValid = ValidationUtils.checkForNullOrInvalidToken(accountVerificationToken);

        String verifiedAccountEmail = null;

        if (isTokenValid) {
            verifiedAccountEmail = this.userService.verifyUserAccount(accountVerificationToken);
        }

        if (isTokenValid && verifiedAccountEmail != null) {
            final String frontendBaseUrl = this.cache.getConfig(CacheConfigKey.FRONTEND_BASE_URL.name(), DEFAULT_FRONTEND_BASE_URL);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", frontendBaseUrl);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        return ResponseUtils.generateAccountVerificationFailResponse();
    }

}
