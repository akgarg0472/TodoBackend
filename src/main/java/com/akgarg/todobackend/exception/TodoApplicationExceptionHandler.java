package com.akgarg.todobackend.exception;

import com.akgarg.todobackend.response.TodoApiResponse;
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
public class TodoApplicationExceptionHandler {

    @ExceptionHandler(TodoException.class)
    public ResponseEntity<TodoApiResponse> handleTodoException(TodoException e) {
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

        return ResponseEntity.status(errorStatusCode).body(TodoUtils.generateTodoApiResponse(e.getMessage(), null, errorStatusCode));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<TodoApiResponse> handleUserException(UserException e) {
        int errorStatusCode;

        switch (e.getMessage()) {
            case INVALID_EMAIL_FORMAT:
            case INVALID_PASSWORD_FORMAT:
            case INVALID_USER_LAST_NAME:
            case NULL_OR_EMPTY_EMAIL:
            case NULL_OR_EMPTY_PASSWORD:
            case EXPIRED_JWT_TOKEN:
            case INVALID_JWT_TOKEN:
            case UNKNOWN_JWT_TOKEN:
            case ACCOUNT_NOT_FOUND_BY_TOKEN:
                errorStatusCode = 400;
                break;

            case INVALID_EMAIL_OR_PASSWORD:
                errorStatusCode = 403;
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
                .body(TodoUtils.generateTodoApiResponse(e.getMessage(), null, errorStatusCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TodoApiResponse> handleGenericException(Exception e) {
        String message;
        int errorStatusCode;

        switch (e.getClass().getSimpleName()) {
            case "HttpRequestMethodNotSupportedException":
                message = "Request Method not allowed";
                errorStatusCode = 405;
                break;
            case "HttpMediaTypeNotSupportedException":
                message = e.getMessage();
                errorStatusCode = 400;
                break;
            case "HttpMessageNotReadableException":
                message = "Invalid request body";
                errorStatusCode = 400;
                break;
            default:
                message = e.getMessage();
                errorStatusCode = 500;
                break;
        }

        return ResponseEntity.status(errorStatusCode).body(TodoUtils.generateTodoApiResponse(message, null, errorStatusCode));
    }

}
