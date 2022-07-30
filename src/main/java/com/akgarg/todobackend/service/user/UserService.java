package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.request.*;
import com.akgarg.todobackend.response.UserResponseDto;

import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface UserService {

    String addNewUser(RegisterUserRequest request, String url);

    UserResponseDto getUserById(String userId);

    UserResponseDto getUserByUsername(String username);

    String updateUserProfile(String userId, UpdateUserRequest updateUserRequest);

    void deleteUser(String userId, String email);

    Map<String, String> login(LoginRequest loginRequest);

    boolean sendForgotPasswordEmail(String email, String url);

    String verifyUserAccount(String accountVerificationToken);

    boolean processForgotPasswordRequest(ForgotPasswordRequest request);

    String changeProfilePassword(String userId, ChangePasswordRequest request);

}
