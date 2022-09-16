package com.akgarg.todobackend.exception;

import com.akgarg.todobackend.response.ApiErrorResponse;
import com.akgarg.todobackend.utils.TodoUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(TodoException.class)
    public ResponseEntity<ApiErrorResponse> handleTodoException(TodoException e) {
        int errorStatusCode;

        switch (e.getMessage()) {
            case NULL_OR_INVALID_TODO_TITLE:
            case NULL_OR_INVALID_USER_ID:
            case NULL_OR_INVALID_REQUEST:
            case NULL_OR_INVALID_VALUE:
            case ERROR_UPDATING_TODO:
            case ERROR_DELETING_TODO:
            case NULL_OR_INVALID_TODO_COMPLETED:
                errorStatusCode = 400;
                break;

            case TODO_NOT_FOUND:
            case NO_TODO_FOUND_FOR_USER:
                errorStatusCode = 404;
                break;

            default:
                errorStatusCode = 500;
                break;
        }

        return ResponseEntity
                .status(errorStatusCode)
                .body(TodoUtils.generateApiErrorResponse(e.getMessage(), errorStatusCode));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserException(UserException e) {
        int errorStatusCode;

        switch (e.getMessage()) {
            case INVALID_EMAIL_FORMAT:
            case INVALID_PASSWORD_FORMAT:
            case INVALID_USER_LAST_NAME:
            case NULL_OR_EMPTY_EMAIL:
            case NULL_OR_EMPTY_PASSWORD:
            case ACCOUNT_NOT_FOUND_BY_TOKEN:
            case PASSWORDS_MISMATCHED:
            case USER_NOT_FOUND_BY_EMAIL:
            case USER_NOT_FOUND_BY_EMAIL_AND_ID:
            case INVALID_LOGOUT_REQUEST:
                errorStatusCode = 400;
                break;

            case INVALID_EMAIL_OR_PASSWORD:
            case USER_ACCOUNT_LOCKED:
            case EXPIRED_JWT_TOKEN:
            case INVALID_AUTH_TOKEN:
            case INVALID_JWT_TOKEN:
            case UNKNOWN_JWT_TOKEN:
                errorStatusCode = 401;
                break;

            case EMAIL_ALREADY_REGISTERED:
                errorStatusCode = 409;
                break;

            default:
                errorStatusCode = 500;
                break;
        }

        return ResponseEntity
                .status(errorStatusCode)
                .body(TodoUtils.generateApiErrorResponse(e.getMessage(), errorStatusCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
        String errorMessage;
        int errorStatusCode;

        switch (e.getClass().getSimpleName()) {
            case "IllegalArgumentException":
                if (INVALID_TOKEN_BIT_PROVIDED.equals(e.getMessage())) {
                    errorMessage = "Invalid token value provided";
                } else {
                    errorMessage = "Invalid value provided";
                }
                errorStatusCode = 400;
                break;

            case "HttpRequestMethodNotSupportedException":
                errorMessage = "Request Method not allowed";
                errorStatusCode = 405;
                break;
            case "HttpMediaTypeNotSupportedException":
                errorMessage = e.getMessage();
                errorStatusCode = 400;
                break;
            case "HttpMessageNotReadableException":
                errorMessage = "Invalid request body";
                errorStatusCode = 400;
                break;
            default:
                errorMessage = e.getMessage();
                errorStatusCode = 500;
                break;
        }

        return ResponseEntity
                .status(errorStatusCode)
                .body(TodoUtils.generateApiErrorResponse(errorMessage, errorStatusCode));
    }

}
