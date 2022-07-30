package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.cache.TodoUserCache;
import com.akgarg.todobackend.config.security.springsecurity.UserDetailsImpl;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.repository.UserRepository;
import com.akgarg.todobackend.request.*;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.email.EmailService;
import com.akgarg.todobackend.service.todo.TodoService;
import com.akgarg.todobackend.utils.DateTimeUtils;
import com.akgarg.todobackend.utils.JwtUtils;
import com.akgarg.todobackend.utils.PasswordUtils;
import com.akgarg.todobackend.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
@SuppressWarnings("all")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final TodoService todoService;
    private final TodoLogger logger;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final TodoUserCache cache;
    private final EmailService emailService;

    @Override
    public String addNewUser(RegisterUserRequest request, String url) {
        TodoUser user = convertRequestToEntity(request);

        user.setId(generateUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(USER_ROLE);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(false);
        user.setAccountVerificationToken(generateTokenFromUserId(user.getId()));
        user.setCreatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        try {
            TodoUser insertedUser = this.userRepository.insert(user);
            logger.info(getClass(), "New user {} saved in database", insertedUser);
            boolean confirmSuccessEmail = emailService.sendAccountVerificationEmail(insertedUser.getEmail(), url, insertedUser.getAccountVerificationToken());
            logger.info(getClass(), "Account confirm email sent successfully: {}", confirmSuccessEmail);
            return insertedUser.getEmail();
        } catch (Exception e) {
            logger.error(getClass(), "Something wrong happened saving new user into db. {}", e.getClass());

            if ("DuplicateKeyException".equals(e.getClass().getSimpleName())) {
                throw new UserException(EMAIL_ALREADY_REGISTERED);
            } else {
                throw e;
            }
        }
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        logger.info(getClass(), "Fetching user with userId: {}", userId);
        TodoUser user = this.fetchUserEntityById(userId, USER_NOT_FOUND_BY_EMAIL);

        return convertEntityToDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        logger.info(getClass(), "Fetching user with username: {}", username);

        TodoUser user;

        Optional<Object> cacheUser = this.cache.getValue(username);
        if (cacheUser.isPresent()) {
            user = (TodoUser) cacheUser.get();
        } else {
            user = this.fetchUserEntityByEmail(username);
        }

        return convertEntityToDto(user);
    }

    @Override
    public String updateUserProfile(String userId, UpdateUserRequest updateUserRequest) {
        logger.info(getClass(), "Updating user {} profile: {}", userId, updateUserRequest);

        TodoUser user = fetchUserEntityById(userId, USER_NOT_FOUND_BY_ID);

        String firstName = updateUserRequest.getFirstName();
        String lastName = updateUserRequest.getLastName();
        String avatar = updateUserRequest.getAvatar();

        boolean isThereAnyUpdate = updateUserEntity(user, firstName, lastName, avatar);
        System.out.println("AnyNewUpdate: " + isThereAnyUpdate);

        if (isThereAnyUpdate) {
            try {
                TodoUser updatedUser = this.userRepository.save(user);
                logger.info(getClass(), "User {} updated successfully: {}", userId, updatedUser);
                this.cache.insertKeyValue(updatedUser.getEmail(), updatedUser);
                return PROFILE_UPDATED_SUCCESSFULLY;
            } catch (Exception e) {
                logger.error(getClass(), "Error updating user {}: {}", userId, e.getMessage());
                return PROFILE_UPDATE_FAILED;
            }
        }

        logger.warn(getClass(), "Error updating user {}: Redundant update", userId);

        return REDUNDANT_PROFILE_UPDATE_REQUEST;
    }

    @Override
    public void deleteUser(String userId, String email) {
        logger.warn(getClass(), "Deleting user with userId {}", userId);

        TodoUser user = this.userRepository.findByIdAndEmail(userId, email).orElseThrow(() -> new UserException(USER_NOT_FOUND_BY_EMAIL_AND_ID));

        this.todoService.removeAllTodoByUserId(userId);

        this.userRepository.delete(user);

        logger.warn(getClass(), "User {} and related info removed from database", user);
    }

    @Override
    public Map<String, String> login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            switch (e.getClass().getSimpleName()) {
                case "BadCredentialsException":
                    throw new UserException(INVALID_EMAIL_OR_PASSWORD);

                case "DisabledException":
                    throw new UserException(USER_ACCOUNT_DISABLED);

                case "LockedException":
                    throw new UserException(USER_ACCOUNT_LOCKED);

                default:
                    throw new UserException(e.getMessage());
            }
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String userRole = "ROLE_USER";
        String userId = null;

        if (userDetails instanceof UserDetailsImpl) {
            cache.insertKeyValue(((UserDetailsImpl) userDetails).getUser().getId(), ((UserDetailsImpl) userDetails).getUser());
            cache.insertKeyValue(userDetails.getUsername(), ((UserDetailsImpl) userDetails).getUser());
            userRole = ((UserDetailsImpl) userDetails).getUser().getRole();
            userId = ((UserDetailsImpl) userDetails).getUser().getId();
        }

        return Map.of(LOGIN_SUCCESS_RESPONSE_TOKEN, this.jwtUtils.generateToken(userDetails),
                      LOGIN_SUCCESS_RESPONSE_ROLE, userRole, LOGIN_SUCCESS_RESPONSE_USERID, userId
        );
    }

    @Override
    public boolean sendForgotPasswordEmail(String email, String url) {
        TodoUser user = fetchUserEntityByEmail(email);

        String forgotPasswordToken = generateTokenFromUserId(user.getId());

        try {
            user.setForgotPasswordToken(forgotPasswordToken);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());
            TodoUser updatedUser = this.userRepository.save(user);
            return this.emailService.sendForgotPasswordEmail(url, updatedUser.getEmail(), forgotPasswordToken);
        } catch (Exception e) {
            logger.error(getClass(), "Something went wrong sending forgot password email to {}", user.getEmail());
        }

        return false;
    }

    @Override
    public String verifyUserAccount(String accountVerificationToken) {
        logger.info(getClass(), "Account verification request received for token: {}", accountVerificationToken);
        String userId = getUserIdFromToken(accountVerificationToken);

        TodoUser user = fetchUserEntityById(userId, ACCOUNT_NOT_FOUND_BY_TOKEN);

        if (accountVerificationToken.equals(user.getAccountVerificationToken())) {
            user.setAccountVerificationToken(null);
            user.setIsAccountNonLocked(true);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            TodoUser updatedUser = this.userRepository.save(user);
            this.emailService.sendAccountConfirmSuccessEmail(updatedUser.getEmail());

            logger.info(getClass(), "User {} verified successfully: {}", userId, updatedUser);
            this.cache.insertKeyValue(updatedUser.getEmail(), updatedUser);
            logger.info(getClass(), "Cache updated for user {}", user.getEmail());

            return user.getEmail();
        }

        logger.error(getClass(), "Account is already verified for token {} and id {}", accountVerificationToken, userId);
        return null;
    }

    @Override
    public boolean processForgotPasswordRequest(ForgotPasswordRequest request) {
        logger.info(getClass(), "Forgot password process request received for: {}", request.getForgotPasswordToken());
        String forgotPasswordToken = request.getForgotPasswordToken();
        String userId = getUserIdFromToken(forgotPasswordToken);

        TodoUser user = fetchUserEntityById(userId, ACCOUNT_NOT_FOUND_BY_TOKEN);

        if (forgotPasswordToken.equals(user.getForgotPasswordToken()) && request.getPassword().equals(request.getConfirmPassword())) {
            user.setForgotPasswordToken(null);
            user.setPassword(this.passwordEncoder.encode(request.getPassword()));
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            TodoUser updatedUser = this.userRepository.save(user);
            this.emailService.sendPasswordSuccessfullyUpdatedEmail(updatedUser.getEmail());

            logger.info(getClass(), "Forgot password success for {}: {}", forgotPasswordToken, user.getEmail());
            this.cache.insertKeyValue(updatedUser.getEmail(), updatedUser);
            logger.info(getClass(), "Cache updated for user {}", user.getEmail());

            return true;
        }

        logger.error(getClass(), "Something went wrong in forgot password processing");

        return false;
    }

    @Override
    public String changeProfilePassword(String userId, ChangePasswordRequest request) {
        TodoUser user = fetchUserEntityById(userId, USER_NOT_FOUND_BY_ID);

        boolean isOldPasswordValidated = PasswordUtils.checkPasswordField(request.getOldPassword());
        boolean isNewPasswordValidated = PasswordUtils.checkPasswordField(request.getPassword());
        boolean isConfirmPasswordValidated = PasswordUtils.checkPasswordField(request.getConfirmPassword());

        if (!isOldPasswordValidated || !isNewPasswordValidated || !isConfirmPasswordValidated) {
            return INVALID_PASSWORD_CHANGE_REQUEST;
        }

        if (!request.getPassword().matches(request.getConfirmPassword())) {
            return PASSWORDS_MISMATCHED;
        }

        isOldPasswordValidated = this.passwordEncoder.matches(request.getOldPassword(), user.getPassword());

        if (!isOldPasswordValidated) {
            return INVALID_OLD_PASSWORD;
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        try {
            TodoUser updatedUser = this.userRepository.save(user);
            this.emailService.sendPasswordSuccessfullyUpdatedEmail(updatedUser.getEmail());
            logger.info(getClass(), "Password changed successfully for {}", userId);
            return PASSWORD_CHANGED_SUCCESSFULLY;
        } catch (Exception e) {
            logger.error(getClass(), "Error changing password: {}", userId);
        }

        return ERROR_CHANGING_PASSWORD;
    }

    private boolean updateUserEntity(TodoUser user, String firstName, String lastName, String avatar) {
        boolean isNewUpdate = false;

        if (UserUtils.isValidStringInput(firstName) && !firstName.equals(user.getFirstName())) {
            user.setFirstName(firstName);
            isNewUpdate = true;
        }

        if (UserUtils.isValidStringInput(lastName) && !lastName.equals(user.getLastName())) {
            user.setLastName(lastName);
            isNewUpdate = true;
        }

        if (UserUtils.isValidStringInput(avatar) && !avatar.equals(user.getAvatar())) {
            user.setAvatar(avatar);
            isNewUpdate = true;
        }

        if (isNewUpdate) {
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());
        }

        return isNewUpdate;
    }

    private TodoUser fetchUserEntityByEmail(String email) {
        Optional<Object> cachedUser = this.cache.getValue(email);

        if (cachedUser.isPresent()) {
            return (TodoUser) cachedUser.get();
        }

        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserException(USER_NOT_FOUND_BY_EMAIL));
    }

    private TodoUser fetchUserEntityById(String userId, String exceptionMessage) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserException(exceptionMessage));
    }

    private TodoUser convertRequestToEntity(RegisterUserRequest request) {
        return this.modelMapper.map(request, TodoUser.class);
    }

    private UserResponseDto convertEntityToDto(TodoUser user) {
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    private String generateUserId() {
        return ObjectId.get().toString();
    }

    private String generateTokenFromUserId(String userId) {
        return PasswordUtils.generateTokenFromId(userId);
    }

    private String getUserIdFromToken(String token) {
        return PasswordUtils.generateIdFromToken(token);
    }

}
