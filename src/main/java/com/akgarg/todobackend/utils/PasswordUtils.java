package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.request.ForgotPasswordRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
public class PasswordUtils {

    private PasswordUtils() {
    }

    public static String generateTokenFromId(String userId) {
        return Base64.getEncoder().encodeToString(userId.getBytes());
    }

    public static String generateIdFromToken(String token) {
        byte[] decodedToken = Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8));

        return new String(decodedToken);
    }

    public static boolean isForgotPasswordRequestValid(ForgotPasswordRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getForgotPasswordToken() == null || request.getForgotPasswordToken().isBlank()) {
            return false;
        }

        return checkPasswordField(request.getPassword()) &&
                checkPasswordField(request.getConfirmPassword()) &&
                request.getPassword().equals(request.getConfirmPassword());
    }

    public static boolean checkPasswordField(String password) {
        Pattern passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            return false;
        }

        return passwordRegexPattern.matcher(password).matches();
    }

}
