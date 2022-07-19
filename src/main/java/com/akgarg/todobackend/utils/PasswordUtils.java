package com.akgarg.todobackend.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

}
