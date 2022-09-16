package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.request.ChangePasswordRequest;
import com.akgarg.todobackend.request.UpdateUserRequest;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.TodoUtils;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.NULL_OR_INVALID_USER_ID;
import static com.akgarg.todobackend.constants.ApplicationConstants.USER_PROFILE_DELETED_SUCCESSFULLY;

/**
 * Author: Akhilesh Garg
 * GitHub:
 * <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 23-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users/user")
public class UserController {

    private final UserService userService;
    private final ApplicationLogger logger;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProfile(@PathVariable("userId") final String userId) {
        logger.info(getClass(), "Received get profile request for {}", userId);
        TodoUtils.checkIdForNullOrInvalid(userId, NULL_OR_INVALID_USER_ID);

        final UserResponseDto userProfile = this.userService.getUserById(userId);
        logger.info(getClass(), "Get profile response {}: {}", userId, userProfile);

        return UserUtils.generateGetProfileResponse(userProfile);
    }

    @PostMapping(value = "/{userId}/update-profile")
    public ResponseEntity<Map<String, Object>> updateUserProfile(
            @RequestBody UpdateUserRequest updateUserRequest, @PathVariable("userId") String userId
    ) {
        logger.info(getClass(), "Received update profile request for {}: {}", userId, updateUserRequest);
        TodoUtils.checkIdForNullOrInvalid(userId, NULL_OR_INVALID_USER_ID);
        final String updateProfileResponse = this.userService.updateUserProfile(userId, updateUserRequest);
        logger.info(getClass(), "Update profile response {}: {}", userId, updateProfileResponse);

        return UserUtils.generateUpdateProfileResponse(updateProfileResponse);
    }

    @PostMapping(value = "/{userId}/change-password")
    public ResponseEntity<Map<String, Object>> changeUserPassword(
            @PathVariable("userId") String userId, @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        logger.info(getClass(), "Received change password changePasswordRequest for {}", userId);
        TodoUtils.checkIdForNullOrInvalid(userId, NULL_OR_INVALID_USER_ID);
        final String changePasswordResponse = this.userService.changeProfilePassword(userId, changePasswordRequest);
        logger.info(getClass(), "Password change response {}: {}", userId, changePasswordResponse);

        return UserUtils.generateUpdateProfileResponse(changePasswordResponse);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUserAccount(
            @PathVariable("userId") String userId, Principal principal
    ) {
        logger.warn(getClass(), "Received delete account request for {}: {}", userId, principal.getName());
        TodoUtils.checkIdForNullOrInvalid(userId, NULL_OR_INVALID_USER_ID);
        this.userService.deleteUser(userId, principal.getName());
        logger.warn(getClass(), "User with id {} & email {} deleted successfully", userId, principal.getName());

        return UserUtils.generateUpdateProfileResponse(USER_PROFILE_DELETED_SUCCESSFULLY);
    }

}
