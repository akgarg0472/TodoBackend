package com.akgarg.todobackend.exception;

import com.akgarg.todobackend.response.ApiErrorResponse;
import com.akgarg.todobackend.utils.DateTimeUtils;
import com.akgarg.todobackend.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(TodoException.class)
    public ResponseEntity<ApiErrorResponse> handleTodoException(final TodoException e) {
        final int errorStatusCode = switch (e.getMessage()) {
            case NULL_OR_INVALID_TODO_TITLE,
                    NULL_OR_INVALID_USER_ID,
                    NULL_OR_INVALID_REQUEST,
                    NULL_OR_INVALID_VALUE,
                    ERROR_UPDATING_TODO,
                    ERROR_DELETING_TODO,
                    NULL_OR_INVALID_TODO_COMPLETED -> 400;

            case TODO_NOT_FOUND, NO_TODO_FOUND_FOR_USER -> 404;

            default -> 500;
        };

        return ResponseEntity.status(errorStatusCode)
                .body(ResponseUtils.generateApiErrorResponse(e.getMessage(), errorStatusCode));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserException(final UserException e) {
        final int errorStatusCode = switch (e.getMessage()) {
            case INVALID_EMAIL_FORMAT,
                    INVALID_PASSWORD_FORMAT,
                    INVALID_USER_LAST_NAME,
                    NULL_OR_EMPTY_EMAIL,
                    NULL_OR_EMPTY_PASSWORD,
                    ACCOUNT_NOT_FOUND_BY_TOKEN,
                    PASSWORDS_MISMATCHED,
                    INVALID_LOGOUT_REQUEST,
                    INVALID_FORGOT_PASSWORD_TOKEN -> 400;

            case INVALID_EMAIL_OR_PASSWORD,
                    USER_ACCOUNT_LOCKED,
                    EXPIRED_JWT_TOKEN,
                    INVALID_AUTH_TOKEN,
                    INVALID_JWT_TOKEN,
                    UNKNOWN_JWT_TOKEN -> 401;

            case ACCESS_DENIED -> 403;

            case USER_NOT_FOUND_BY_EMAIL,
                    USER_NOT_FOUND_BY_EMAIL_AND_ID -> 404;

            case EMAIL_ALREADY_REGISTERED -> 409;

            default -> 500;
        };

        return ResponseEntity.status(errorStatusCode)
                .body(ResponseUtils.generateApiErrorResponse(e.getMessage(), errorStatusCode));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(final BadRequestException e) {
        final var errorResponse = new ApiErrorResponse();

        errorResponse.setErrorCode(400);
        errorResponse.setErrorMessage(e.getMessage());
        errorResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(final GenericException e) {
        final var errorResponse = new ApiErrorResponse();

        errorResponse.setErrorCode(500);
        errorResponse.setErrorMessage(e.getMessage());
        errorResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(final Exception e) {
        String errorMessage;
        int errorStatusCode;

        switch (e.getClass().getSimpleName()) {
            case "IllegalArgumentException" -> {
                if (INVALID_TOKEN_BIT_PROVIDED.equals(e.getMessage())) {
                    errorMessage = "Invalid token value provided";
                } else {
                    errorMessage = "Invalid value provided";
                }
                errorStatusCode = 400;
            }

            case "HttpRequestMethodNotSupportedException" -> {
                errorMessage = "Request Method not allowed";
                errorStatusCode = 405;
            }

            case "HttpMediaTypeNotSupportedException" -> {
                errorMessage = e.getMessage();
                errorStatusCode = 400;
            }

            case "HttpMessageNotReadableException" -> {
                errorMessage = "Invalid request body";
                errorStatusCode = 400;
            }

            default -> {
                errorMessage = INTERNAL_SERVER_ERROR;
                errorStatusCode = 500;
            }
        }

        return ResponseEntity.status(errorStatusCode)
                .body(ResponseUtils.generateApiErrorResponse(errorMessage, errorStatusCode));
    }

}
