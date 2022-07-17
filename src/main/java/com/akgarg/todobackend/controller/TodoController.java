package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.request.NewTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.response.TodoApiResponse;
import com.akgarg.todobackend.response.TodoResponseDto;
import com.akgarg.todobackend.service.todo.TodoService;
import com.akgarg.todobackend.utils.TodoUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;
    private final TodoLogger logger;

    @RequestMapping(value = "/todo/{todoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleTodo(@PathVariable String todoId, Principal principal) {
        logger.info(getClass(), "Request received from {} to getSingleTodo for todoId: {}", principal.getName(), todoId);

        TodoUtils.checkIdForNullOrInvalid(todoId, NULL_OR_INVALID_TODO_ID);
        TodoResponseDto todo = this.todoService.getTodoById(todoId);

        return ResponseEntity.ok(TodoUtils.generateTodoApiResponse(null, todo, 200));
    }


    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTodosForCurrentUser(@PathVariable String userId, Principal principal) {
        logger.info(getClass(), "Request received from {} to getTodos for userId: {}", principal.getName(), userId);

        TodoUtils.checkIdForNullOrInvalid(userId, NULL_OR_INVALID_USER_ID);
        List<TodoResponseDto> todos = this.todoService.getTodosForUser(userId);

        return ResponseEntity.ok(TodoUtils.generateTodoApiResponse(null, todos, 200));
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> addTodoNote(
            @RequestBody NewTodoRequest newTodoRequest, Principal principal
    ) {
        logger.info(getClass(), "Request received from {} to add new todo: {}", principal.getName(), newTodoRequest);

        TodoUtils.checkNewTodoRequest(newTodoRequest);
        TodoResponseDto insertedTodo = this.todoService.insert(newTodoRequest);

        return ResponseEntity.status(201).body(TodoUtils.generateTodoApiResponse(TODO_CREATED_SUCCESSFULLY, insertedTodo, 201));
    }


    @RequestMapping(value = "/{todoId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> removeTodoNote(@PathVariable String todoId, Principal principal) {
        logger.info(getClass(), "Request received from {} to remove todo with todoId: {}", principal.getName(), todoId);

        TodoUtils.checkIdForNullOrInvalid(todoId, NULL_OR_INVALID_TODO_ID);
        this.todoService.delete(todoId);

        return ResponseEntity.ok(TodoUtils.generateTodoApiResponse("Todo deleted successfully", null, 200));
    }


    @RequestMapping(value = "todo/{todoId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> updateTodo(
            @RequestBody UpdateTodoRequest updateTodoRequest, @PathVariable String todoId, Principal principal
    ) {
        logger.info(getClass(), "Request received from {} to update todo with todoId: {}", principal.getName(), todoId);

        TodoUtils.checkIdForNullOrInvalid(todoId, NULL_OR_INVALID_TODO_ID);
        TodoUtils.checkUpdateTodoRequest(updateTodoRequest);
        TodoResponseDto updatedTodo = this.todoService.update(todoId, updateTodoRequest);

        return ResponseEntity.ok(TodoUtils.generateTodoApiResponse(TODO_UPDATED_SUCCESSFULLY, updatedTodo, 200));
    }

    @RequestMapping(value = "/todo/{todoId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> updateTodoStatus(
            @PathVariable String todoId, @RequestBody UpdateTodoStatusRequest request, Principal principal
    ) {
        logger.info(getClass(), "Request received from {} to update todo status with todoId: {}", principal.getName(), todoId);

        TodoUtils.checkIdForNullOrInvalid(todoId, NULL_OR_INVALID_TODO_ID);
        TodoUtils.checkUpdateTodoStatusRequest(request);
        TodoResponseDto updatedTodo = this.todoService.updateTodoStatus(todoId, request);

        return ResponseEntity.ok(TodoUtils.generateTodoApiResponse(TODO_STATUS_UPDATED_SUCCESSFULLY, updatedTodo, 200));
    }

}
