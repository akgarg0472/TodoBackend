package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.request.ChangePasswordRequest;
import com.akgarg.todobackend.model.request.UpdateUserRequest;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.NULL_OR_INVALID_USER_ID;
import static com.akgarg.todobackend.constants.ApplicationConstants.USER_PROFILE_DELETED_SUCCESSFULLY;

/**
 * @author Akhilesh Garg
 * @since 23-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users/user")
public class UserController {

    private final UserService userService;
    private final ApplicationLogger logger;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProfile(final @PathVariable("userId") String userId) {
        logger.info(getClass(), "Received get profile request for {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final var userProfile = this.userService.getUserById(userId);
        logger.info(getClass(), "Get profile response {}: {}", userId, userProfile);

        return ResponseUtils.generateGetProfileResponse(userProfile);
    }

    @PostMapping(value = "/{userId}/update-profile")
    public ResponseEntity<Map<String, Object>> updateUserProfile(
            final @RequestBody UpdateUserRequest updateUserRequest,
            final @PathVariable("userId") String userId
    ) {
        logger.info(getClass(), "Received update profile request for {}: {}", userId, updateUserRequest);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final String updateProfileResponse = this.userService.updateUserProfile(userId, updateUserRequest);
        logger.info(getClass(), "Update profile response {}: {}", userId, updateProfileResponse);

        return ResponseUtils.generateUpdateProfileResponse(updateProfileResponse);
    }

    @PostMapping(value = "/{userId}/change-password")
    public ResponseEntity<Map<String, Object>> changeUserPassword(
            final @PathVariable("userId") String userId,
            final @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        logger.info(getClass(), "Received change password changePasswordRequest for {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final String changePasswordResponse = this.userService.changeProfilePassword(userId, changePasswordRequest);
        logger.info(getClass(), "Password change response {}: {}", userId, changePasswordResponse);

        return ResponseUtils.generateUpdateProfileResponse(changePasswordResponse);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUserAccount(
            final @PathVariable("userId") String userId
    ) {
        logger.warn(getClass(), "Received delete account request for userId {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        this.userService.deleteUser(userId);
        logger.warn(getClass(), "User with id {} deleted successfully", userId);

        return ResponseUtils.generateUpdateProfileResponse(USER_PROFILE_DELETED_SUCCESSFULLY);
    }

}
