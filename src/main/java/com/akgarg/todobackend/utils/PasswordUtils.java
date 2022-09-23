package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import org.modelmapper.internal.bytebuddy.utility.RandomString;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

import static com.akgarg.todobackend.constants.ApplicationConstants.INVALID_FORGOT_PASSWORD_TOKEN;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
public class PasswordUtils {

    private PasswordUtils() {
    }

    public static String generateTokenFromId(final String userId) {
        return Base64.getEncoder().encodeToString(userId.getBytes());
    }

    public static String generateIdFromToken(final String token) {
        final byte[] decodedToken = Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8));

        return new String(decodedToken);
    }

    public static boolean isForgotPasswordRequestValid(final ForgotPasswordRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getForgotPasswordToken() == null || request.getForgotPasswordToken().isBlank()) {
            return false;
        }

        return checkPasswordField(request.getPassword()) && checkPasswordField(request.getConfirmPassword()) && request.getPassword().equals(request.getConfirmPassword());
    }

    public static boolean checkPasswordField(final String password) {
        final var passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            return false;
        }

        return passwordRegexPattern.matcher(password).matches();
    }

    public static String generateForgotPasswordToken() {
        return RandomString.make(48);
    }

    public static String hashForgotPasswordToken(String token, String userId) {
        final String rawToken = userId.substring(0, 12) + token + userId.substring(12);

        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }

    public static String[] decodeForgotPasswordToken(String token) {
        final byte[] decodedTokenBytes = Base64.getDecoder().decode(token);
        final String decodedToken = new String(decodedTokenBytes);

        if (decodedToken.length() != 72) {
            throw new UserException(INVALID_FORGOT_PASSWORD_TOKEN);
        }

        final String userId = decodedToken.substring(0, 12) + decodedToken.substring(60);
        final String passwordResetToken = decodedToken.substring(12, 60);

        return new String[]{passwordResetToken, userId};
    }

}
