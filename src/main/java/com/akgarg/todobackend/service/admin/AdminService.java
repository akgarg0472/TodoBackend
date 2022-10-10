package com.akgarg.todobackend.service.admin;

import com.akgarg.todobackend.model.dto.TodoUserDto;
import com.akgarg.todobackend.model.response.AdminDashboardInfo;
import com.akgarg.todobackend.model.response.PaginatedUserResponse;
import com.akgarg.todobackend.model.response.UserResponseDto;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 21-07-2022
 */
public interface AdminService {

    AdminDashboardInfo adminDashboard();

    UserResponseDto getAdminProfile(String adminId);

    PaginatedUserResponse getAllUsers(int offset, int limit);

    TodoUserDto getProfile(String profileId);

    boolean lockUserAccount(String userId, String reason, String lockedBy);

    boolean unlockUserAccount(String userId, String reason, String by);

    boolean terminateUserAccount(String userId, String reason, String by);

    boolean enableUserAccount(String userId, String reason, String by);

    boolean changeAccountType(String userId, String accountType, final String by);

}
