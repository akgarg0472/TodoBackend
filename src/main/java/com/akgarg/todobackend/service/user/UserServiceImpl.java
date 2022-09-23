package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.config.security.springsecurity.UserDetailsImpl;
import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.UserRepository;
import com.akgarg.todobackend.request.*;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.email.EmailService;
import com.akgarg.todobackend.service.todo.TodoService;
import com.akgarg.todobackend.utils.DateTimeUtils;
import com.akgarg.todobackend.utils.JwtUtils;
import com.akgarg.todobackend.utils.PasswordUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;
import static com.akgarg.todobackend.constants.FrontendConstants.DEFAULT_FRONTEND_BASE_URL;
import static com.akgarg.todobackend.constants.FrontendConstants.DEFAULT_PASSWORD_RESET_ENDPOINT_URL;

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
    private final ApplicationLogger logger;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final ApplicationCache cache;
    private final EmailService emailService;

    @Override
    public String addNewUser(RegisterUserRequest request, String url) {
        final var user = convertRequestToEntity(request);

        user.setId(generateUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ROLE_USER);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(false);
        user.setAccountVerificationToken(generateTokenFromUserId(user.getId()));
        user.setCreatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        try {
            final var insertedUser = this.userRepository.insert(user);
            logger.info(getClass(), "New user {} saved in database", insertedUser);
            boolean confirmSuccessEmail = emailService.sendAccountVerificationEmail(insertedUser.getEmail(), insertedUser.getName(), url, insertedUser.getAccountVerificationToken());
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
        final var user = this.fetchUserEntityById(userId, USER_NOT_FOUND_BY_ID);

        return convertUserEntityToDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        logger.info(getClass(), "Fetching user with username: {}", username);

        TodoUser user;

        final var cacheUser = this.cache.getValue(username);
        if (cacheUser.isPresent()) {
            user = (TodoUser) cacheUser.get();
        } else {
            user = this.fetchUserEntityByEmail(username);
        }

        return convertUserEntityToDto(user);
    }

    @Override
    public String updateUserProfile(String userId, UpdateUserRequest updateUserRequest) {
        logger.info(getClass(), "Updating user {} profile: {}", userId, updateUserRequest);

        final var user = fetchUserEntityById(userId, USER_NOT_FOUND_BY_ID);
        final String firstName = updateUserRequest.getFirstName();
        final String lastName = updateUserRequest.getLastName();
        final String avatar = updateUserRequest.getAvatar();

        boolean isThereAnyUpdate = updateUserEntity(user, firstName, lastName, avatar);
        System.out.println("AnyNewUpdate: " + isThereAnyUpdate);

        if (isThereAnyUpdate) {
            try {
                final var updatedUser = this.userRepository.save(user);
                logger.info(getClass(), "User {} updated successfully: {}", userId, updatedUser);
                this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);
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

        final var user = this.userRepository
                .findByIdAndEmail(userId, email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND_BY_EMAIL_AND_ID));

        this.todoService.removeAllTodoByUserId(userId);
        this.userRepository.delete(user);

        logger.warn(getClass(), "User {} and related info removed from database", user);
    }

    @Override
    public Map<String, String> login(LoginRequest loginRequest) {
        try {
            final var authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
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

        final var userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String userRole = "ROLE_USER";
        String userId = null;
        String email = null;
        String name = null;

        if (userDetails instanceof UserDetailsImpl) {
            cache.insertOrUpdateUserKeyValue(((UserDetailsImpl) userDetails).getUser().getId(), ((UserDetailsImpl) userDetails).getUser());
            cache.insertOrUpdateUserKeyValue(userDetails.getUsername(), ((UserDetailsImpl) userDetails).getUser());
            userRole = ((UserDetailsImpl) userDetails).getUser().getRole();
            userId = ((UserDetailsImpl) userDetails).getUser().getId();
            email = ((UserDetailsImpl) userDetails).getUser().getEmail();
            name = ((UserDetailsImpl) userDetails).getUser().getName();
        }

        return Map.of(LOGIN_SUCCESS_RESPONSE_TOKEN, this.jwtUtils.generateToken(userDetails),
                      LOGIN_SUCCESS_RESPONSE_ROLE, userRole, LOGIN_SUCCESS_RESPONSE_USERID, userId,
                      LOGIN_SUCCESS_RESPONSE_EMAIL, email, LOGIN_SUCCESS_RESPONSE_NAME, name
        );
    }

    @Override
    public boolean sendForgotPasswordEmail(String email, String url) {
        final var user = fetchUserEntityByEmail(email);
        final String forgotPasswordToken = PasswordUtils.generateForgotPasswordToken();

        try {
            user.setForgotPasswordToken(forgotPasswordToken);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            final var updatedUser = this.userRepository.save(user);
            final String hashedToken = PasswordUtils.hashForgotPasswordToken(forgotPasswordToken, updatedUser.getId());
            final String frontEndResetPasswordEndpointUrl = getFrontEndResetPasswordEndpointUrl();

            return this.emailService.sendForgotPasswordEmail(frontEndResetPasswordEndpointUrl, updatedUser.getName(), updatedUser.getEmail(), hashedToken);
        } catch (Exception e) {
            logger.error(getClass(), "Something went wrong sending forgot password email to {}", user.getEmail());
        }

        return false;
    }

    @Override
    public String verifyUserAccount(String accountVerificationToken) {
        logger.info(getClass(), "Account verification request received for token: {}", accountVerificationToken);

        final String userId = getUserIdFromToken(accountVerificationToken);
        final var user = fetchUserEntityById(userId, ACCOUNT_NOT_FOUND_BY_TOKEN);

        if (accountVerificationToken.equals(user.getAccountVerificationToken())) {
            user.setAccountVerificationToken(null);
            user.setIsAccountNonLocked(true);
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            TodoUser updatedUser = this.userRepository.save(user);
            this.emailService.sendAccountVerificationSuccessEmail(updatedUser.getEmail(), updatedUser.getName());

            logger.info(getClass(), "User {} verified successfully: {}", userId, updatedUser);
            this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);
            logger.info(getClass(), "Cache updated for user {}", user.getEmail());

            return user.getEmail();
        }

        logger.error(getClass(), "Account is already verified for token {} and id {}", accountVerificationToken, userId);
        return null;
    }

    @Override
    public boolean processForgotPasswordRequest(ForgotPasswordRequest request) {
        logger.info(getClass(), "Forgot password process request received for: {}", request.getForgotPasswordToken());

        final String requestForgotPasswordToken = request.getForgotPasswordToken();
        final var decodedForgotPasswordToken = PasswordUtils.decodeForgotPasswordToken(requestForgotPasswordToken);
        final String forgotPasswordToken = decodedForgotPasswordToken[0];
        final String userId = decodedForgotPasswordToken[1];
        final TodoUser user = fetchUserEntityById(userId, ACCOUNT_NOT_FOUND_BY_TOKEN);

        if (forgotPasswordToken != null &&
                forgotPasswordToken.equals(user.getForgotPasswordToken()) &&
                request.getPassword().equals(request.getConfirmPassword())) {
            user.setForgotPasswordToken(null);
            user.setPassword(this.passwordEncoder.encode(request.getPassword()));
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

            final TodoUser updatedUser = this.userRepository.save(user);
            this.emailService.sendPasswordSuccessfullyUpdatedEmail(updatedUser.getEmail(), updatedUser.getName());

            logger.info(getClass(), "Forgot password success for {}: {}", forgotPasswordToken, user.getEmail());
            this.cache.insertOrUpdateUserKeyValue(updatedUser.getEmail(), updatedUser);
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
            this.emailService.sendPasswordSuccessfullyUpdatedEmail(updatedUser.getEmail(), updatedUser.getName());
            logger.info(getClass(), "Password changed successfully for {}", userId);
            return PASSWORD_CHANGED_SUCCESSFULLY;
        } catch (Exception e) {
            logger.error(getClass(), "Error changing password: {}", userId);
        }

        return ERROR_CHANGING_PASSWORD;
    }

    private String getFrontEndResetPasswordEndpointUrl() {
        final Object frontEndBaseUrl = this.cache.getConfig(CacheConfigKey.FRONTEND_BASE_URL.name(), DEFAULT_FRONTEND_BASE_URL);
        final Object frontendPasswordResetEndpointUrl = this.cache.getConfig(CacheConfigKey.RESET_PASSWORD_PAGE_URL.name(), DEFAULT_PASSWORD_RESET_ENDPOINT_URL);

        return frontEndBaseUrl + "/" + frontendPasswordResetEndpointUrl;
    }

    private boolean updateUserEntity(TodoUser user, String firstName, String lastName, String avatar) {
        boolean isNewUpdate = false;

        if (!ValidationUtils.isStringInvalid(firstName) && !firstName.equals(user.getFirstName())) {
            user.setFirstName(firstName);
            isNewUpdate = true;
        }

        if (!ValidationUtils.isStringInvalid(lastName) && !lastName.equals(user.getLastName())) {
            user.setLastName(lastName);
            isNewUpdate = true;
        }

        if (!ValidationUtils.isStringInvalid(avatar) && !avatar.equals(user.getAvatar())) {
            user.setAvatar(avatar);
            isNewUpdate = true;
        }

        if (isNewUpdate) {
            user.setLastUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());
        }

        return isNewUpdate;
    }

    private TodoUser fetchUserEntityByEmail(String email) {
        final var cachedUser = this.cache.getValue(email);

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

    private UserResponseDto convertUserEntityToDto(TodoUser user) {
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    private String generateUserId() {
        return ObjectId.get().toString();
    }

    private String generateTokenFromUserId(String userId) {
        final String encodedUserId = PasswordUtils.generateTokenFromId(userId);
        return encodedUserId;
    }

    private String getUserIdFromToken(String token) {
        return PasswordUtils.generateIdFromToken(token);
    }

}
