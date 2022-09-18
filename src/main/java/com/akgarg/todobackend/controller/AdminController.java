package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.request.ChangeAccountStateRequest;
import com.akgarg.todobackend.request.ChangeAccountTypeRequest;
import com.akgarg.todobackend.response.PaginatedUserResponse;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.admin.AdminService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public class AdminController {

    private final ApplicationLogger logger;
    private final AdminService adminService;

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<Map<String, Object>> getAdminProfile(@PathVariable("adminId") final String adminId) {
        this.logger.debug(getClass(), "getAdminProfile(): {}", adminId);

        final UserResponseDto profile = this.adminService.getAdminProfile(adminId);

        return ResponseUtils.generateGetProfileResponse(profile);
    }

    @GetMapping
    public Map<String, Object> getAllUsers(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "offset", defaultValue = "10") int limit
    ) {
        this.logger.debug(getClass(), "getAllUsers(): {}->{}", offset, limit);

        final PaginatedUserResponse users = this.adminService.getAllUsers(offset, limit);

        return ResponseUtils.generateGetAllUsersResponse(users);
    }


    @PatchMapping("/changeAccountType")
    public ResponseEntity<Map<String, Object>> changeAccountType(@RequestBody ChangeAccountTypeRequest request) {
        this.logger.debug(getClass(), "changeAccountType(): {}", request);
        ValidationUtils.validateChangeAccountTypeRequest(request);

        final boolean changeAccountTypeResponse = this.adminService
                .changeAccountType(request.getUserId(), request.getAccountType());

        return ResponseUtils.generateChangeAccountTypeResponse(changeAccountTypeResponse);
    }

    @PatchMapping("/user/lockAccount")
    public ResponseEntity<Map<String, Object>> lockAccount(@RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "lockAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean lockStateChangeResponse = this.adminService
                .lockUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateAccountLockStateChangeResponse(lockStateChangeResponse);
    }

    @PatchMapping("/user/unlockAccount")
    public ResponseEntity<Map<String, Object>> unlockAccount(@RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "unlockAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean lockStateChangeResponse = this.adminService
                .unlockUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateAccountLockStateChangeResponse(lockStateChangeResponse);
    }

    @PatchMapping("/user/disableAccount")
    public ResponseEntity<Map<String, Object>> disableAccount(@RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "disableAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean enableStateChangeResponse = this.adminService
                .terminateUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateAccountEnabledStateChangeResponse(enableStateChangeResponse);
    }

    @PatchMapping("/user/enableAccount")
    public ResponseEntity<Map<String, Object>> enableAccount(@RequestBody ChangeAccountStateRequest request) {
        this.logger.debug(getClass(), "enableAccount(): {}", request);
        ValidationUtils.validateChangeAccountStateRequest(request);

        final boolean enableStateChangeResponse = this.adminService
                .enableUserAccount(request.getUserId(), request.getReason(), request.getBy());

        return ResponseUtils.generateAccountEnabledStateChangeResponse(enableStateChangeResponse);
    }

}
