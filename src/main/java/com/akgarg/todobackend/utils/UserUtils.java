package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;

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
        response.setTimestamp(TimeUtils.getCurrentDateTimeInMilliseconds());

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

    public static void checkForgotPasswordRequest(ForgotPasswordRequest forgotPasswordRequest) {
        if (forgotPasswordRequest == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        String email = forgotPasswordRequest.getEmail();
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
        Pattern passwordRegexPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            throw new UserException(NULL_OR_EMPTY_PASSWORD);
        }

        if (!passwordRegexPattern.matcher(password).matches()) {
            throw new UserException(INVALID_PASSWORD_FORMAT);
        }
    }

    public static Map<String, Object> generateForgotPasswordResponse(boolean emailResponse, String email) {
        Map<String, Object> response = new HashMap<>();

        String responseMessage = emailResponse ?
                FORGOT_PASSWORD_EMAIL_SUCCESS.replace("$email", email) :
                FORGOT_PASSWORD_EMAIL_FAILURE.replace("$email", email);

        response.put("message", responseMessage);
        response.put("success", emailResponse);
        response.put("timestamp", TimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

}
