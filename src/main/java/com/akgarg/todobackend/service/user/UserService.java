package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.request.UpdateUserRequest;
import com.akgarg.todobackend.response.UserResponseDto;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface UserService {

    String addNewUser(RegisterUserRequest request, String url);

    UserResponseDto getUserById(String userId);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUser(String userId, UpdateUserRequest updateUserRequest);

    UserResponseDto deleteUser(String userId, String email);

    String login(LoginRequest loginRequest);

    boolean sendForgotPasswordEmail(String email, String url);

    String verifyUserAccount(String accountVerificationToken);

}
