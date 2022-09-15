package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.request.NewTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.response.ApiErrorResponse;
import com.akgarg.todobackend.response.TodoApiResponse;
import org.bson.types.ObjectId;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public class TodoUtils {

    private TodoUtils() {
        super();
    }

    public static void checkNewTodoRequest(final NewTodoRequest newTodoRequest) {
        if (newTodoRequest == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        final String title = newTodoRequest.getTitle();
        final String userId = newTodoRequest.getUserId();
        final String description = newTodoRequest.getDescription();

        if (userId == null || userId.trim().isBlank() || !ObjectId.isValid(userId)) {
            throw new TodoException(NULL_OR_INVALID_USER_ID);
        }

        if (title == null || title.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_TITLE);
        }

        if (description == null || description.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_DESCRIPTION);
        }

    }

    public static TodoApiResponse generateTodoApiResponse(final String message, final Object data, final int status) {
        final TodoApiResponse response = new TodoApiResponse();

        response.setSuccess(status < 400 || status > 599);
        response.setMessage(message);
        response.setData(data);
        response.setStatus(status);
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static void checkForNullOrInvalidValue(final Object object) {
        if (object == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        if (object instanceof String && ((String) object).trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_VALUE);
        }
    }

    public static void checkIdForNullOrInvalid(final String id, final String exceptionMessage) {
        if (id == null || id.trim().isBlank() || !ObjectId.isValid(id)) {
            throw new TodoException(exceptionMessage);
        }
    }

    public static void checkUpdateTodoRequest(final UpdateTodoRequest updateTodoRequest) {
        if (updateTodoRequest == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        final String title = updateTodoRequest.getTitle();
        final String description = updateTodoRequest.getDescription();
        final Boolean completed = updateTodoRequest.getCompleted();

        if (title == null || title.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_TITLE);
        }

        if (description == null || description.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_DESCRIPTION);
        }

        if (completed == null) {
            throw new TodoException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

    public static void checkUpdateTodoStatusRequest(final UpdateTodoStatusRequest request) {
        if (request == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        if (request.getCompleted() == null) {
            throw new TodoException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

    public static ApiErrorResponse generateApiErrorResponse(final String errorMessage, final int errorCode) {
        final ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setErrorCode(errorCode);
        errorResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return errorResponse;
    }

}
