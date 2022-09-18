package com.akgarg.todobackend.service.admin;

import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.exception.GenericException;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.UserRepository;
import com.akgarg.todobackend.response.PaginatedUserResponse;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 21-07-2022
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ApplicationLogger logger;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public UserResponseDto getAdminProfile(final String adminId) {
        logger.info(getClass(), "getAdminProfile(): {}", adminId);

        return this.userService.getUserById(adminId);
    }

    @Override
    public PaginatedUserResponse getAllUsers(final int offset, final int limit) {
        logger.info(getClass(), "getAllUsers(): {}->{}", offset, limit);

        try {
            return this.userRepository.getAllUsers(offset, limit);
        } catch (Exception e) {
            return PaginatedUserResponse.emptyResponse();
        }
    }

    @Override
    public boolean lockUserAccount(final String userId, String reason, String by) {
        logger.info(getClass(), "lockUserAccount(): {}->'{}' by {}", userId, reason, by);

        return this.changeUserAccountLockState(userId, false, reason, by);
    }

    @Override
    public boolean unlockUserAccount(final String userId, String reason, String by) {
        logger.info(getClass(), "unlockUserAccount(): {}->'{}' by {}", userId, reason, by);

        return this.changeUserAccountLockState(userId, true, reason, by);
    }

    @Override
    public boolean terminateUserAccount(final String userId, final String reason, final String by) {
        logger.info(getClass(), "terminateUserAccount(): {}->'{}' by {}", userId, reason, by);

        return changeUserAccountEnableState(userId, false, reason, by);
    }

    @Override
    public boolean enableUserAccount(final String userId, final String reason, final String by) {
        logger.info(getClass(), "terminateUserAccount(): {}->'{}' by {}", userId, reason, by);

        return changeUserAccountEnableState(userId, true, reason, by);
    }

    @Override
    public boolean changeAccountType(final String userId, final String accountType) {
        logger.info(getClass(), "changeAccountType(): {}->{}", userId, accountType);

        final String _accountType = getAccountType(accountType);

        if (ADMIN_ROLE.equals(_accountType) || USER_ROLE.equals(_accountType)) {
            return changeUserAccountType(userId, _accountType);
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

            this.userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new UserException(ACCOUNT_LOCK_STATE_UPDATED_FAILED);
        }
    }

    private boolean changeUserAccountEnableState(
            final String userId, final boolean enabled, final String reason,
            final String by
    ) {
        final TodoUser user = getUserEntity(userId);

        try {
            user.setIsEnabled(enabled);

            if (enabled) {
                user.setEnabledBy(by);
                user.setEnableReason(reason);
            } else {
                user.setDisabledBy(by);
                user.setDisableReason(reason);
            }

            this.userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new UserException(ACCOUNT_ENABLED_STATE_CHANGE_FAILED);
        }
    }

    private boolean changeUserAccountType(final String userId, final String accountType) {
        final TodoUser user = getUserEntity(userId);

        try {
            user.setRole(accountType);
            this.userRepository.save(user);
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
            return ADMIN_ROLE;
        } else {
            return USER_ROLE;
        }
    }

}
