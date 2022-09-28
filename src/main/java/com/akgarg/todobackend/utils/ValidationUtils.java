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
 * @author Akhilesh Garg
 * @since 18-09-2022
 */
public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateNewTodoRequest(final NewTodoRequest newTodoRequest) {
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

    public static void validateUpdateTodoRequest(final UpdateTodoRequest updateTodoRequest) {
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

    public static void validateUpdateTodoStatusRequest(final UpdateTodoStatusRequest request) {
        if (request == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        } else if (request.getCompleted() == null) {
            throw new BadRequestException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

    public static void validateRegisterUserRequest(final RegisterUserRequest request) {
        if (request == null) {
            throw new UserException(NULL_OR_INVALID_REQUEST);
        }

        final String email = request.getEmail();
        final String password = request.getPassword();
        final String confirmPassword = request.getConfirmPassword();
        final String lastName = request.getLastName();

        validateRequestEmailField(email);
        validateRequestPasswordField(password);
        validateRequestPasswordField(confirmPassword);

        if (lastName == null || lastName.trim().isBlank()) {
            throw new BadRequestException(INVALID_USER_LAST_NAME);
        } else if (!password.equals(confirmPassword)) {
            throw new BadRequestException(PASSWORDS_MISMATCHED);
        }
    }

    public static boolean validateForgotPasswordRequest(final ForgotPasswordRequest request) {
        if (request == null) {
            return false;
        }

        if (request.getForgotPasswordToken() == null || request.getForgotPasswordToken().isBlank()) {
            return false;
        }

        return validatePasswordField(request.getPassword()) &&
                validatePasswordField(request.getConfirmPassword()) &&
                request.getPassword().equals(request.getConfirmPassword());
    }

    public static boolean validatePasswordField(final String password) {
        final var passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            return false;
        }

        return passwordRegexPattern.matcher(password).matches();
    }


    public static void validateLoginRequest(final LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        final String email = loginRequest.getEmail();
        final String password = loginRequest.getPassword();

        validateRequestEmailField(email);
        validateRequestPasswordField(password);
    }

    public static void validateForgotPasswordEmailRequest(final ForgotPasswordEmailRequest forgotPasswordEmailRequest) {
        if (forgotPasswordEmailRequest == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        }

        validateRequestEmailField(forgotPasswordEmailRequest.getEmail());
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

    public static void validateEmailSenderProperties(final EmailSenderConfigProperties config) {
        if (config == null ||
                config.getHost() == null ||
                config.getPort() == 0 ||
                config.getSenderEmail() == null ||
                config.getSenderEmailPassword() == null) {
            throw new NullPointerException("Invalid email config properties");
        }
    }

    public static void checkForNullOrInvalidId(final String id, final String exceptionMessage) {
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

    private static void validateRequestEmailField(final String email) {
        final Pattern emailRegexPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (email == null || email.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_EMPTY_EMAIL);
        } else if (!emailRegexPattern.matcher(email).matches()) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }
    }

    public static void validateConfigPropUpdateRequest(final ConfigPropUpdateRequest request) {
        if (request == null) {
            throw new BadRequestException(NULL_OR_INVALID_REQUEST);
        } else if (isStringInvalid(request.getKey())) {
            throw new BadRequestException(INVALID_CONFIG_PROPS_KEY);
        } else if (isStringInvalid(request.getValue())) {
            throw new BadRequestException(INVALID_CONFIG_PROPS_VALUE);
        }
    }

    private static void validateRequestPasswordField(final String password) {
        final Pattern passwordRegexPattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        if (password == null || password.trim().isBlank()) {
            throw new BadRequestException(NULL_OR_EMPTY_PASSWORD);
        } else if (!passwordRegexPattern.matcher(password).matches()) {
            throw new BadRequestException(INVALID_PASSWORD_FORMAT);
        }
    }

}
