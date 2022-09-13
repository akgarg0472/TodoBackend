package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.request.ForgotPasswordEmailRequest;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;
import com.akgarg.todobackend.response.SignupResponse;
import com.akgarg.todobackend.response.UserResponseDto;
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

    public static void checkRegisterUserRequest(final RegisterUserRequest request) {
        if (request == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        final String email = request.getEmail();
        final String password = request.getPassword();
        final String confirmPassword = request.getConfirmPassword();
        final String lastName = request.getLastName();

        checkEmailField(email);
        checkPasswordField(password);
        checkPasswordField(confirmPassword);

        if (lastName == null || lastName.trim().isBlank()) {
            throw new UserException(INVALID_USER_LAST_NAME);
        }

        if (!password.equals(confirmPassword)) {
            throw new UserException(PASSWORDS_MISMATCHED);
        }
    }

    public static LoginResponse generateLoginSuccessRequest(final Map<String, String> loginProps) {
        final LoginResponse response = new LoginResponse();

        response.setAuthToken(loginProps.get(LOGIN_SUCCESS_RESPONSE_TOKEN));
        response.setRole(loginProps.get(LOGIN_SUCCESS_RESPONSE_ROLE));
        response.setUserId(loginProps.get(LOGIN_SUCCESS_RESPONSE_USERID));
        response.setName(loginProps.get(LOGIN_SUCCESS_RESPONSE_NAME));
        response.setEmail(loginProps.get(LOGIN_SUCCESS_RESPONSE_EMAIL));
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static void checkLoginRequest(final LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        final String email = loginRequest.getEmail();
        final String password = loginRequest.getPassword();

        checkEmailField(email);
        checkPasswordField(password);
    }

    public static void checkForgotPasswordRequest(final ForgotPasswordEmailRequest forgotPasswordEmailRequest) {
        if (forgotPasswordEmailRequest == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        checkEmailField(forgotPasswordEmailRequest.getEmail());
    }

    private static void checkEmailField(final String email) {
        final Pattern emailRegexPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (email == null || email.trim().isBlank()) {
            throw new UserException(NULL_OR_EMPTY_EMAIL);
        }

        if (!emailRegexPattern.matcher(email).matches()) {
            throw new UserException(INVALID_EMAIL_FORMAT);
        }
    }

    private static void checkPasswordField(final String password) {
        final Pattern passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            throw new UserException(NULL_OR_EMPTY_PASSWORD);
        }

        if (!passwordRegexPattern.matcher(password).matches()) {
            throw new UserException(INVALID_PASSWORD_FORMAT);
        }
    }

    public static Map<String, Object> generateForgotPasswordResponse(final boolean emailResponse, final String email) {
        final Map<String, Object> response = new HashMap<>();

        final String responseMessage = emailResponse ?
                FORGOT_PASSWORD_EMAIL_SUCCESS.replace("$email", email) :
                FORGOT_PASSWORD_EMAIL_FAILURE.replace("$email", email);

        response.put(MESSAGE, responseMessage);
        response.put(SUCCESS, emailResponse);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static ResponseEntity<Map<String, Object>> generateAccountVerificationResponse(
            final boolean isTokenValid, final String verifiedAccountEmail
    ) {
        final Map<String, Object> response = new HashMap<>();

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

    public static boolean checkForNullOrInvalidToken(final String token) {
        return token != null && !token.isBlank();
    }

    public static ResponseEntity<Map<String, Object>> generateForgotPasswordCompleteResponse(
            final boolean isRequestValid, final boolean passwordResetResponse
    ) {
        final Map<String, Object> response = new HashMap<>();
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

    public static boolean isValidStringInput(final String str) {
        return str != null && !str.isBlank();
    }

    public static ResponseEntity<Map<String, Object>> generateUpdateProfileResponse(final String updateResponse) {
        final Map<String, Object> response = new HashMap<>();
        int statusCode;

        switch (updateResponse) {
            case PROFILE_UPDATED_SUCCESSFULLY:
            case PASSWORD_CHANGED_SUCCESSFULLY:
            case USER_PROFILE_DELETED_SUCCESSFULLY:
                statusCode = 200;
                break;

            case REDUNDANT_PROFILE_UPDATE_REQUEST:
            case INVALID_OLD_PASSWORD:
            case PASSWORDS_MISMATCHED:
            case NULL_OR_INVALID_REQUEST:
            case INVALID_PASSWORD_CHANGE_REQUEST:
                statusCode = 400;
                break;

            default:
                statusCode = 500;
                break;
        }

        response.put(MESSAGE, updateResponse);
        response.put(STATUS, statusCode);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(statusCode).body(response);
    }

    public static SignupResponse generateSignupResponse(final String message, final int status) {
        final SignupResponse signupResponse = new SignupResponse();

        signupResponse.setMessage(message);
        signupResponse.setStatus(status);
        signupResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return signupResponse;
    }

    public static ResponseEntity<Map<String, Object>> generateGetProfileResponse(final UserResponseDto userProfile) {
        Map<String, Object> response = new HashMap<>();
        int statusCode = userProfile == null ? 404 : 200;

        response.put("user", userProfile);
        response.put(STATUS, statusCode);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(statusCode).body(response);
    }

}
