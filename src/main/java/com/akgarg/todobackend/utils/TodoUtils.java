package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.request.NewTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.response.TodoApiResponse;
import org.bson.types.ObjectId;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public class TodoUtils {

    public static void checkNewTodoRequest(NewTodoRequest newTodoRequest) {
        if (newTodoRequest == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        String title = newTodoRequest.getTitle();
        String userId = newTodoRequest.getUserId();

        if (title == null || title.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_TITLE);
        }

        if (userId == null || userId.trim().isBlank() || !ObjectId.isValid(userId)) {
            throw new TodoException(NULL_OR_INVALID_USER_ID);
        }
    }

    public static TodoApiResponse generateTodoApiResponse(String message, Object data, int status) {
        TodoApiResponse response = new TodoApiResponse();

        response.setSuccess(status < 400 || status > 599);
        response.setMessage(message);
        response.setData(data);
        response.setStatus(status);
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static void checkForNullOrInvalidValue(Object object) {
        if (object == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        if (object instanceof String && ((String) object).trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_VALUE);
        }
    }

    public static void checkIdForNullOrInvalid(String id, String exceptionMessage) {
        if (id == null || id.trim().isBlank() || !ObjectId.isValid(id)) {
            throw new TodoException(exceptionMessage);
        }
    }

    public static void checkUpdateTodoRequest(UpdateTodoRequest updateTodoRequest) {
        if (updateTodoRequest == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        String title = updateTodoRequest.getTitle();
        String description = updateTodoRequest.getDescription();

        if (title == null || title.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_TITLE);
        }

        if (description == null || description.trim().isBlank()) {
            throw new TodoException(NULL_OR_INVALID_TODO_DESCRIPTION);
        }

    }

    public static void checkUpdateTodoStatusRequest(UpdateTodoStatusRequest request) {
        if (request == null) {
            throw new TodoException(NULL_OR_INVALID_REQUEST);
        }

        if (request.getCompleted() == null) {
            throw new TodoException(NULL_OR_INVALID_TODO_COMPLETED);
        }
    }

}
