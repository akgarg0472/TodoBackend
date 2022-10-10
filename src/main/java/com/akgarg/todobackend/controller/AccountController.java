package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.request.LoginRequest;
import com.akgarg.todobackend.model.request.RegisterUserRequest;
import com.akgarg.todobackend.model.response.LoginResponse;
import com.akgarg.todobackend.model.response.SignupResponse;
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

import static com.akgarg.todobackend.constants.ApplicationConstants.REGISTRATION_SUCCESS_CONFIRM_ACCOUNT;
import static com.akgarg.todobackend.constants.FrontendConstants.DEFAULT_FRONTEND_BASE_URL;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
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
            final @RequestBody RegisterUserRequest registerUserRequest,
            final HttpServletRequest httpServletRequest
    ) {
        logger.info(getClass(), "Signup request received: {}", registerUserRequest);
        ValidationUtils.validateRegisterUserRequest(registerUserRequest);

        final String registeredEmail = this.userService.addNewUser(registerUserRequest, UrlUtils.getUrl(httpServletRequest));

        return ResponseUtils.generateSignupSuccessResponse(REGISTRATION_SUCCESS_CONFIRM_ACCOUNT.replace("$email", registeredEmail), 201);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(final @RequestBody LoginRequest loginRequest) {
        logger.info(getClass(), "Login request received: {}", loginRequest);
        ValidationUtils.validateLoginRequest(loginRequest);

        final var loginResponse = this.userService.login(loginRequest);

        return ResponseUtils.generateLoginSuccessRequest(loginResponse);
    }

    @GetMapping(value = "/verify/{accountVerificationToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyUserAccount(final @PathVariable String accountVerificationToken) {
        final boolean isTokenValid = ValidationUtils.checkForNullOrInvalidToken(accountVerificationToken);

        String verifiedAccountEmail = null;

        if (isTokenValid) {
            verifiedAccountEmail = this.userService.verifyUserAccount(accountVerificationToken);
        }

        if (isTokenValid && verifiedAccountEmail != null) {
            final String frontendBaseUrl = this.cache.getConfigValue(CacheConfigKey.FRONTEND_BASE_URL.name(), DEFAULT_FRONTEND_BASE_URL);

            final HttpHeaders headers = new HttpHeaders();
            headers.add("Location", frontendBaseUrl);

            final String cookie = "AccountVerified=true; VerifiedAccountEmail=" + verifiedAccountEmail + ";";
            headers.add("Set-Cookie", cookie);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        return ResponseUtils.generateAccountVerificationFailResponse();
    }

}
