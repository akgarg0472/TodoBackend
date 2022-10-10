package com.akgarg.todobackend.service.todo;

import com.akgarg.todobackend.model.request.NewTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.model.response.PaginatedTodoResponse;
import com.akgarg.todobackend.model.response.TodoResponseDto;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
public interface TodoService {

    TodoResponseDto insert(NewTodoRequest request);

    void delete(String todoId);

    TodoResponseDto getTodoById(String todoId);

    PaginatedTodoResponse getTodosForUser(String userId, int offset, int limit);

    TodoResponseDto update(String todoId, UpdateTodoRequest updateTodoRequest);

    TodoResponseDto updateTodoStatus(String todoId, UpdateTodoStatusRequest request);

    void removeAllTodoByUserId(String userId);

    PaginatedTodoResponse getCompletedTodosForUser(String userId, int offset, int limit);

    PaginatedTodoResponse getPendingTodosForUser(String userId, int offset, int limit);

}
