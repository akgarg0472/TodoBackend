package com.akgarg.todobackend.service.todo;

import com.akgarg.todobackend.request.NewTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.response.TodoResponseDto;

import java.util.List;

/**
 * TodoApplication service object
 * <p>
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface TodoService {

    TodoResponseDto insert(NewTodoRequest request);

    void delete(String todoId);

    TodoResponseDto getTodoById(String todoId);

    List<TodoResponseDto> getTodosForUser(String userId);

    TodoResponseDto update(String todoId, UpdateTodoRequest updateTodoRequest);

    TodoResponseDto updateTodoStatus(String todoId, UpdateTodoStatusRequest request);

    void removeAllTodoByUserId(String userId);

}