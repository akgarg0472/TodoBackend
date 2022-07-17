package com.akgarg.todobackend.service.user;

import com.akgarg.todobackend.cache.TodoUserCache;
import com.akgarg.todobackend.config.security.springsecurity.UserDetailsImpl;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.repository.UserRepository;
import com.akgarg.todobackend.request.LoginRequest;
import com.akgarg.todobackend.request.RegisterUserRequest;
import com.akgarg.todobackend.request.UpdateUserRequest;
import com.akgarg.todobackend.response.UserResponseDto;
import com.akgarg.todobackend.service.email.EmailService;
import com.akgarg.todobackend.service.todo.TodoService;
import com.akgarg.todobackend.utils.JwtUtils;
import com.akgarg.todobackend.utils.TimeUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
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
    public String addNewUser(RegisterUserRequest request) {
        TodoUser user = convertRequestToEntity(request);

        user.setId(generateUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(USER_ROLE);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(false);
        user.setCreatedAt(TimeUtils.getCurrentDateTimeInMilliseconds());

        try {
            TodoUser insertedUser = this.userRepository.insert(user);
            logger.info(getClass(), "User {} saved in database", insertedUser);
            return insertedUser.getEmail();
        } catch (Exception e) {
            logger.error(getClass(), "Something wrong happened saving new user into db. {}", e.getMessage());

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
        TodoUser user = this.fetchUserEntityById(userId);

        return convertEntityToDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        logger.info(getClass(), "Fetching user with username: {}", username);
        TodoUser user = this.fetchUserEntityByEmail(username);

        return convertEntityToDto(user);
    }

    @Override
    public UserResponseDto updateUser(String userId, UpdateUserRequest updateUserRequest) {
        logger.info(getClass(), "Updating user {} profile: {}", userId, updateUserRequest);

        TodoUser user = fetchUserEntityById(userId);
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setAvatar(updateUserRequest.getAvatar());

        TodoUser updatedUser = this.userRepository.save(user);

        logger.info(getClass(), "User {} updated successfully: {}", userId, updatedUser);

        return convertEntityToDto(updatedUser);
    }

    @Override
    public UserResponseDto deleteUser(String userId, String email) {
        logger.info(getClass(), "Deleting user with userId {} & email {}");

        TodoUser user = this.userRepository.findByIdAndEmail(userId, email).orElseThrow(() -> new UserException(USER_NOT_FOUND));

        this.todoService.removeAllTodoByUserId(userId);

        this.userRepository.delete(user);

        logger.info(getClass(), "User {} removed from database", user);

        return convertEntityToDto(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
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

        if (userDetails instanceof UserDetailsImpl) {
            cache.insertKeyValue(((UserDetailsImpl) userDetails).getUser().getId(), ((UserDetailsImpl) userDetails).getUser());
            cache.insertKeyValue(userDetails.getUsername(), ((UserDetailsImpl) userDetails).getUser());
        }

        return this.jwtUtils.generateToken(userDetails);
    }

    @Override
    public boolean sendForgotPasswordEmail(String email) {
        fetchUserEntityByEmail(email);

        return this.emailService.sendForgotPasswordEmail(email);
    }
        
    private TodoUser fetchUserEntityByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    private TodoUser fetchUserEntityById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    private String generateUserId() {
        return ObjectId.get().toString();
    }

    private TodoUser convertRequestToEntity(RegisterUserRequest request) {
        return this.modelMapper.map(request, TodoUser.class);
    }

    private UserResponseDto convertEntityToDto(TodoUser user) {
        return this.modelMapper.map(user, UserResponseDto.class);
    }

}