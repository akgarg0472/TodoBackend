package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.request.ChangeAccountStateRequest;
import com.akgarg.todobackend.model.request.ChangeAccountTypeRequest;
import com.akgarg.todobackend.service.admin.AdminService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * @author Akhilesh Garg
 * @since 19-07-2022
 */
@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public class AdminController {

    private final ApplicationLogger logger;
    private final AdminService adminService;

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        this.logger.debug(getClass(), "Admin dashboard request");

        final var adminDashboard = this.adminService.adminDashboard();

        return ResponseUtils.generateAdminDashboardResponse(adminDashboard);
    }

    @GetMapping("/admin/profile/{adminId}")
    public ResponseEntity<Map<String, Object>> getAdminProfile(final @PathVariable("adminId") String adminId) {
        this.logger.debug(getClass(), "getAdminProfile(): {}", adminId);
        ValidationUtils.checkForNullOrInvalidId(adminId, NULL_OR_INVALID_USER_ID);

        final var profile = this.adminService.getAdminProfile(adminId);

        return ResponseUtils.generateGetProfileResponse(profile);
    }

    @GetMapping("/admin/users")
    public Map<String, Object> getAllUsers(
            final @RequestParam(value = "offset", defaultValue = "0") int offset,
            final @RequestParam(value = "offset", defaultValue = "10") int limit
    ) {
        this.logger.debug(getClass(), "getAllUsers(): {}->{}", offset, limit);

        final var users = this.adminService.getAllUsers(offset, limit);

        return ResponseUtils.generateGetAllUsersResponse(users);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<Map<String, Object>> getProfile(final @PathVariable("profileId") String profileId) {
        this.logger.debug(getClass(), "getProfile(): {}", profileId);
        ValidationUtils.checkForNullOrInvalidId(profileId, NULL_OR_INVALID_USER_ID);

        final var profile = this.adminService.getProfile(profileId);

        System.out.println("Detailed user profile is: " + profile);

        return ResponseUtils.generateGetProfileResponse(profile);
    }

    @PatchMapping("/changeAccountType")
    public ResponseEntity<Map<String, Object>> changeAccountType(final @RequestBody ChangeAccountTypeRequest request) {
        this.logger.debug(getClass(), "changeAccountType(): {}", request);
        ValidationUtils.validateChangeAccountTypeRequest(request);

        final boolean changeAccountTypeResponse = this.adminService
                .changeAccountType(request.getUserId(), request.getAccountType(), request.getBy());

        return ResponseUtils.generateChangeAccountTypeResponse(changeAccountTypeResponse);
    }

    @PatchMapping("/user/lockAccount")
    public ResponseEntity<Map<String, Object>> lockAccount(final @RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "lockAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean lockStateChangeResponse = this.adminService
                .lockUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateBooleanConditionalResponse(lockStateChangeResponse, ACCOUNT_LOCK_STATE_UPDATED_SUCCESS, ACCOUNT_LOCK_STATE_UPDATED_FAILED);
    }

    @PatchMapping("/user/unlockAccount")
    public ResponseEntity<Map<String, Object>> unlockAccount(final @RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "unlockAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean lockStateChangeResponse = this.adminService
                .unlockUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateBooleanConditionalResponse(lockStateChangeResponse, ACCOUNT_LOCK_STATE_UPDATED_SUCCESS, ACCOUNT_LOCK_STATE_UPDATED_FAILED);
    }

    @PatchMapping("/user/disableAccount")
    public ResponseEntity<Map<String, Object>> disableAccount(final @RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "disableAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean enableStateChangeResponse = this.adminService
                .terminateUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateBooleanConditionalResponse(enableStateChangeResponse, ACCOUNT_ENABLED_STATE_CHANGE_SUCCESS, ACCOUNT_ENABLED_STATE_CHANGE_FAILED);
    }

    @PatchMapping("/user/enableAccount")
    public ResponseEntity<Map<String, Object>> enableAccount(final @RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "enableAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean enableStateChangeResponse = this.adminService
                .enableUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateBooleanConditionalResponse(enableStateChangeResponse, ACCOUNT_ENABLED_STATE_CHANGE_SUCCESS, ACCOUNT_ENABLED_STATE_CHANGE_FAILED);
    }

}
