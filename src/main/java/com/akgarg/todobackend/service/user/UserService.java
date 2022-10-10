package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.model.request.*;
import com.akgarg.todobackend.model.response.UserResponseDto;

import java.util.Map;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
public interface UserService {

    String addNewUser(RegisterUserRequest request, String url);

    UserResponseDto getUserById(String userId);

    UserResponseDto getUserByUsername(String username);

    String updateUserProfile(
            String userId,
            UpdateUserRequest updateUserRequest
    );

    void deleteUser(String userId);

    Map<String, String> login(LoginRequest loginRequest);

    boolean sendForgotPasswordEmail(String email, String url);

    String verifyUserAccount(String accountVerificationToken);

    boolean processForgotPasswordRequest(ForgotPasswordRequest request);

    String changeProfilePassword(
            String userId,
            ChangePasswordRequest request
    );

}
