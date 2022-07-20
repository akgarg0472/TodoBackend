package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.request.ForgotPasswordEmailRequest;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public class UserUtils {

    public static final String MESSAGE = "message";
    public static final String SUCCESS = "success";
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";

    private UserUtils() {
    }

    public static void checkRegisterUserRequest(RegisterUserRequest request) {
        if (request == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        String email = request.getEmail();
        String password = request.getPassword();
        String lastName = request.getLastName();

        checkEmailField(email);
        checkPasswordField(password);

        if (lastName == null || lastName.trim().isBlank()) {
            throw new UserException(INVALID_USER_LAST_NAME);
        }
    }

    public static LoginResponse generateLoginSuccessRequest(String token) {
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setSuccess(true);
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static void checkLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        checkEmailField(email);
        checkPasswordField(password);
    }

    public static void checkForgotPasswordRequest(ForgotPasswordEmailRequest forgotPasswordEmailRequest) {
        if (forgotPasswordEmailRequest == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        String email = forgotPasswordEmailRequest.getEmail();
        checkEmailField(email);
    }

    private static void checkEmailField(String email) {
        Pattern emailRegexPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (email == null || email.trim().isBlank()) {
            throw new UserException(NULL_OR_EMPTY_EMAIL);
        }

        if (!emailRegexPattern.matcher(email).matches()) {
            throw new UserException(INVALID_EMAIL_FORMAT);
        }
    }

    private static void checkPasswordField(String password) {
        Pattern passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            throw new UserException(NULL_OR_EMPTY_PASSWORD);
        }

        if (!passwordRegexPattern.matcher(password).matches()) {
            throw new UserException(INVALID_PASSWORD_FORMAT);
        }
    }

    public static Map<String, Object> generateForgotPasswordResponse(boolean emailResponse, String email) {
        Map<String, Object> response = new HashMap<>();

        String responseMessage = emailResponse ? FORGOT_PASSWORD_EMAIL_SUCCESS.replace("$email", email) : FORGOT_PASSWORD_EMAIL_FAILURE.replace("$email", email);

        response.put(MESSAGE, responseMessage);
        response.put(SUCCESS, emailResponse);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static ResponseEntity<Map<String, Object>> generateAccountVerificationResponse(
            boolean isTokenValid, String verifiedAccountEmail
    ) {
        Map<String, Object> response = new HashMap<>();

        String responseMessage;
        int responseStatusCode;

        if (!isTokenValid) {
            responseMessage = ACCOUNT_VERIFICATION_TOKEN_INVALID;
            responseStatusCode = 400;
        } else if (verifiedAccountEmail != null) {
            responseMessage = ACCOUNT_VERIFIED_SUCCESSFUL.replace("$EMAIL", verifiedAccountEmail);
            responseStatusCode = 200;
        } else {
            responseMessage = ACCOUNT_VERIFICATION_FAILED;
            responseStatusCode = 400;
        }

        response.put(MESSAGE, responseMessage);
        response.put(STATUS, responseStatusCode);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(responseStatusCode).body(response);
    }

    public static boolean checkForNullOrInvalidToken(String token) {
        return token != null && !token.isBlank();
    }

    public static ResponseEntity<Map<String, Object>> generateForgotPasswordCompleteResponse(
            boolean isRequestValid, boolean passwordResetResponse
    ) {
        Map<String, Object> response = new HashMap<>();
        String responseMessage;
        int statusCode;

        if (!isRequestValid) {
            responseMessage = INVALID_FORGOT_PASSWORD_REQUEST;
            statusCode = 400;
        } else if (!passwordResetResponse) {
            responseMessage = PASSWORD_RESET_FAILED;
            statusCode = 400;
        } else {
            responseMessage = PASSWORD_RESET_SUCCESS;
            statusCode = 200;
        }

        response.put(MESSAGE, responseMessage);
        response.put(SUCCESS, isRequestValid);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(statusCode).body(response);
    }

}
