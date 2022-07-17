package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.request.ForgotPasswordRequest;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.response.LoginResponse;
import com.akgarg.todobackend.response.TodoApiResponse;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.TodoUtils;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.akgarg.todobackend.constants.ApplicationConstants.REGISTRATION_SUCCESS_CONFIRM_ACCOUNT;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final TodoLogger logger;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        logger.info(getClass(), "Signup request received: {}", registerUserRequest);

        UserUtils.checkRegisterUserRequest(registerUserRequest);
        String email = this.userService.addNewUser(registerUserRequest);

        return ResponseEntity.status(201).body(TodoUtils.generateTodoApiResponse(REGISTRATION_SUCCESS_CONFIRM_ACCOUNT.replace("$email", email), null, 201));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        UserUtils.checkLoginRequest(loginRequest);
        logger.info(getClass(), "Login request received: {}", loginRequest.getEmail());

        String token = this.userService.login(loginRequest);

        return ResponseEntity.ok(UserUtils.generateLoginSuccessRequest(token));
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendForgotPasswordEmail(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        logger.info(getClass(), "Forgot password request received for: {}", forgotPasswordRequest);

        UserUtils.checkForgotPasswordRequest(forgotPasswordRequest);

        String email = forgotPasswordRequest.getEmail();
        boolean forgotPasswordEmailResponse = this.userService.sendForgotPasswordEmail(email);
        int responseStatus = forgotPasswordEmailResponse ? 200 : 500;

        return ResponseEntity
                .status(responseStatus)
                .body(UserUtils.generateForgotPasswordResponse(forgotPasswordEmailResponse, email));
    }

}
