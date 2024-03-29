package com.akgarg.todobackend.service.admin;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.exception.GenericException;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.dto.AdminDashboardUserInfoDto;
import com.akgarg.todobackend.model.dto.TodoUserDto;
import com.akgarg.todobackend.model.entity.TodoUser;
import com.akgarg.todobackend.model.response.AdminDashboardInfo;
import com.akgarg.todobackend.model.response.PaginatedUserResponse;
import com.akgarg.todobackend.model.response.UserResponseDto;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.repository.UserRepository;
import com.akgarg.todobackend.service.user.UserService;
import com.akgarg.todobackend.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * @author Akhilesh Garg
 * @since 21-07-2022
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ApplicationLogger logger;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final UserService userService;
    private final ApplicationCache cache;
    private final ModelMapper modelMapper;

    @Override
    public AdminDashboardInfo adminDashboard() {
        try {
            final AdminDashboardInfo response = new AdminDashboardInfo();
            long adminsCount = 0L;
            long usersCount = 0L;
            long activeAccountsCount = 0L;
            long todosCount;
            long blockedAccountsCount = 0L;

            final var accounts = this.userRepository.getAdminDashboardInfo();
            todosCount = this.todoRepository.count();

            for (AdminDashboardUserInfoDto account : accounts) {
                if (ROLE_USER.equalsIgnoreCase(account.getRole())) {
                    usersCount++;
                }
                if (ROLE_ADMIN.equalsIgnoreCase(account.getRole())) {
                    adminsCount++;
                }
                if (account.isAccountNonLocked()) {
                    activeAccountsCount++;
                }
                if (!account.isAccountNonLocked()) {
                    blockedAccountsCount++;
                }
            }

            response.setTotalAccountsCount((long) accounts.size());
            response.setTotalAdminsCount(adminsCount);
            response.setTotalUsersCount(usersCount);
            response.setTotalTodosCount(todosCount);
            response.setActiveAccountsCount(activeAccountsCount);
            response.setBlockedAccountsCount(blockedAccountsCount);
            response.setEnabledAccountsCount(accounts.size() - blockedAccountsCount);

            return response;
        } catch (Exception e) {
            logger.error(getClass(), "Error getting admin dashboard info: {}", e.getMessage());
            throw new GenericException();
        }
    }

    @Override
    public UserResponseDto getAdminProfile(final String adminId) {
        logger.info(getClass(), "getAdminProfile(): {}", adminId);

        return this.userService.getUserById(adminId);
    }

    @Override
    public PaginatedUserResponse getAllUsers(
            final int offset,
            final int limit
    ) {
        logger.info(getClass(), "getAllUsers(): {}->{}", offset, limit);

        try {
            return this.userRepository.getAllUsers(offset, limit);
        } catch (Exception e) {
            return PaginatedUserResponse.emptyResponse();
        }
    }

    @Override
    public TodoUserDto getProfile(final String profileId) {
        final TodoUser userEntity = this.getUserEntity(profileId);

        return this.modelMapper.map(userEntity, TodoUserDto.class);
    }

    @Override
    public boolean lockUserAccount(
            final String userId,
            final String reason,
            final String lockedBy
    ) {
        logger.info(getClass(), "lockUserAccount(): {}->'{}' by {}", userId, reason, lockedBy);

        return this.changeUserAccountLockState(userId, false, reason, lockedBy);
    }

    @Override
    public boolean unlockUserAccount(
            final String userId,
            final String reason,
            final String by
    ) {
        logger.info(getClass(), "unlockUserAccount(): {}->'{}' by {}", userId, reason, by);

        return this.changeUserAccountLockState(userId, true, reason, by);
    }

    @Override
    public boolean terminateUserAccount(
            final String userId,
            final String reason,
            final String terminatedBy
    ) {
        logger.info(getClass(), "terminateUserAccount(): {}->'{}' by {}", userId, reason, terminatedBy);

        return changeUserAccountEnableState(userId, false, reason, terminatedBy);
    }

    @Override
    public boolean enableUserAccount(
            final String userId,
            final String reason,
            final String by
    ) {
        logger.info(getClass(), "terminateUserAccount(): {}->'{}' by {}", userId, reason, by);

        return changeUserAccountEnableState(userId, true, reason, by);
    }

    @Override
    public boolean changeAccountType(
            final String userId,
            final String accountType,
            final String by
    ) {
        logger.info(getClass(), "changeAccountType(): {}->{}", userId, accountType);

        final String _accountType = getAccountType(accountType);

        if (ROLE_ADMIN.equals(_accountType) || ROLE_USER.equals(_accountType)) {
            return changeUserAccountType(userId, _accountType, by);
        } else {
            throw new GenericException(INVALID_ACCOUNT_TYPE);
        }
    }

    private boolean changeUserAccountLockState(
            final String userId,
            final boolean accountNonLocked,
            final String reason,
            final String by
    ) {
        final TodoUser user = getUserEntity(userId);

        try {
            user.setIsAccountNonLocked(accountNonLocked);

            if (accountNonLocked) {
                user.setUnlockedBy(by);
                user.setUnlockReason(reason);

            } else {
                user.setLockedBy(by);
                user.setLockReason(reason);
            }

            final TodoUser updatedUser = this.userRepository.save(user);
            this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);

            return true;
        } catch (Exception e) {
            throw new UserException(ACCOUNT_LOCK_STATE_UPDATED_FAILED);
        }
    }

    private boolean changeUserAccountEnableState(
            final String userId,
            final boolean enabled,
            final String reason,
            final String accountStateChangedBy
    ) {
        final TodoUser user = getUserEntity(userId);

        try {
            user.setIsEnabled(enabled);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            if (enabled) {
                user.setEnabledBy(accountStateChangedBy);
                user.setEnableReason(reason);
            } else {
                user.setDisabledBy(accountStateChangedBy);
                user.setDisableReason(reason);
            }

            final var updatedUser = this.userRepository.save(user);
            this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);

            return true;
        } catch (Exception e) {
            throw new UserException(ACCOUNT_ENABLED_STATE_CHANGE_FAILED);
        }
    }

    private boolean changeUserAccountType(
            final String userId,
            final String accountType,
            final String by
    ) {
        final TodoUser user = getUserEntity(userId);

        try {
            user.setRole(accountType);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            if (ROLE_ADMIN.equalsIgnoreCase(accountType)) {
                user.setApprovedAsAdminBy(by);
                user.setApprovedAsAdminOn(DateTimeUtils.getCurrentDateTimeInMilliseconds());
            } else {
                user.setApprovedAsAdminBy(null);
                user.setApprovedAsAdminOn(null);
            }

            final TodoUser updatedUser = this.userRepository.save(user);
            this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);
            return true;
        } catch (Exception e) {
            throw new UserException(ERROR_UPDATING_ACC_TYPE);
        }
    }

    private TodoUser getUserEntity(final String userId) {
        return this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND_BY_ID));
    }

    private String getAccountType(final String accountType) {
        if ("ADMIN".equalsIgnoreCase(accountType)) {
            return ROLE_ADMIN;
        } else {
            return ROLE_USER;
        }
    }

}
