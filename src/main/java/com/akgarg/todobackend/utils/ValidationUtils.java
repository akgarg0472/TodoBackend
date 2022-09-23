package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.config.email.EmailSenderConfigProperties;
import com.akgarg.todobackend.exception.BadRequestException;
import com.akgarg.todobackend.exception.GenericException;
import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.request.*;
import org.bson.types.ObjectId;

import java.util.regex.Pattern;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 18-09-2022
 */
public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void checkNewTodoRequest(final NewTodoRequest newTodoRequest) {
        if (newTodoRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        final String title = newTodoRequest.getTitle();
        final String userId = newTodoRequest.getUserId();
        final String description = newTodoRequest.getDescription();

        if (userId == null || userId.trim().isBlank() || !ObjectId.isValid(userId)) {
            throw new BadRequestException(NULL_OR_INVALID_USER_ID);
        } else if (title == null || title.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_TITLE);
        } else if (description == null || description.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_DESCRIPTION);
        }
    }

    public static void checkUpdateTodoRequest(final UpdateTodoRequest updateTodoRequest) {
        if (updateTodoRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        final String title = updateTodoRequest.getTitle();
        final String description = updateTodoRequest.getDescription();
        final Boolean completed = updateTodoRequest.getCompleted();

        if (title == null || title.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_TITLE);
        } else if (description == null || description.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_DESCRIPTION);
        } else if (completed == null) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

    public static void checkUpdateTodoStatusRequest(final UpdateTodoStatusRequest request) {
        if (request == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        } else if (request.getCompleted() == null) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

    public static void checkRegisterUserRequest(final RegisterUserRequest request) {
        if (request == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        final String email = request.getEmail();
        final String password = request.getPassword();
        final String confirmPassword = request.getConfirmPassword();
        final String lastName = request.getLastName();

        checkEmailField(email);
        checkPasswordField(password);
        checkPasswordField(confirmPassword);

        if (lastName == null || lastName.trim().isBlank()) {
            throw new BadRequestException(INVALID_USER_LAST_NAME);
        } else if (!password.equals(confirmPassword)) {
            throw new BadRequestException(PASSWORDS_MISMATCHED);
        }
    }

    public static void checkLoginRequest(final LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        final String email = loginRequest.getEmail();
        final String password = loginRequest.getPassword();

        checkEmailField(email);
        checkPasswordField(password);
    }

    public static void checkForgotPasswordRequest(final ForgotPasswordEmailRequest forgotPasswordEmailRequest) {
        if (forgotPasswordEmailRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        checkEmailField(forgotPasswordEmailRequest.getEmail());
    }

    public static void validateChangeAccountTypeRequest(final ChangeAccountTypeRequest request) {
        if (request == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        } else if (isStringInvalid(request.getUserId())) {
            throw new BadRequestException();
        } else if (isStringInvalid(request.getAccountType())) {
            throw new BadRequestException();
        } else if (isStringInvalid(request.getBy())) {
            throw new BadRequestException();
        }
    }

    public static void validateChangeAccountStateRequest(final ChangeAccountStateRequest request) {
        if (request == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        } else if (isStringInvalid(request.getUserId())) {
            throw new BadRequestException();
        } else if (isStringInvalid(request.getReason())) {
            throw new BadRequestException();
        } else if (isStringInvalid(request.getBy())) {
            throw new BadRequestException();
        }
    }

    public static boolean isStringInvalid(final String str) {
        return str == null || str.isBlank();
    }

    public static void checkEmailSenderProperties(final EmailSenderConfigProperties config) {
        if (config == null || config.getHost() == null || config.getPort() == 0 || config.getSenderEmail() == null || config.getSenderEmailPassword() == null) {
            throw new NullPointerException("Invalid email config properties");
        }
    }

    public static void checkIdForNullOrInvalid(final String id, final String exceptionMessage) {
        if (id == null || id.trim().isBlank() || !ObjectId.isValid(id)) {
            throw new GenericException(exceptionMessage);
        }
    }

    public static void checkForNullOrInvalidValue(final Object object) {
        if (object == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        if (object instanceof String && ((String) object).trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_VALUE);
        }
    }

    public static boolean checkForNullOrInvalidToken(final String token) {
        return token != null && !token.isBlank();
    }

    private static void checkEmailField(final String email) {
        final var emailRegexPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (email == null || email.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_EMPTY_EMAIL);
        } else if (!emailRegexPattern.matcher(email).matches()) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }
    }

    private static void checkPasswordField(final String password) {
        final var passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_EMPTY_PASSWORD);
        } else if (!passwordRegexPattern.matcher(password).matches()) {
            throw new BadRequestException(INVALID_PASSWORD_FORMAT);
        }
    }

}
