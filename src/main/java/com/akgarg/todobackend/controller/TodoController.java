package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.request.NewTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.model.response.PaginatedTodoResponse;
import com.akgarg.todobackend.model.response.TodoApiResponse;
import com.akgarg.todobackend.service.todo.TodoService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;
    private final ApplicationLogger logger;

    @GetMapping(value = "/todo/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> getTodoUsingTodoId(
            final @PathVariable String todoId
    ) {
        logger.info(getClass(), "Request received to getSingleTodo for todoId: {}", todoId);
        ValidationUtils.checkForNullOrInvalidId(todoId, NULL_OR_INVALID_TODO_ID);

        final var todo = this.todoService.getTodoById(todoId);

        return ResponseUtils.generateTodoApiResponse(null, todo, 200);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedTodoResponse> getTodosForCurrentUser(
            final @PathVariable String userId,
            final @RequestParam(value = "limit", defaultValue = "10") int limit,
            final @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        logger.info(getClass(), "Request received to getTodos for userId: {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final var response = this.todoService.getTodosForUser(userId, offset, limit);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/completed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedTodoResponse> getCompletedTodos(
            final @PathVariable("userId") String userId,
            final @RequestParam(value = "limit", defaultValue = "10") int limit,
            final @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        logger.info(getClass(), "Request for completed todos: {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final var response = this.todoService.getCompletedTodosForUser(userId, offset, limit);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/pending/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedTodoResponse> getPendingTodos(
            final @PathVariable("userId") String userId,
            final @RequestParam(value = "limit", defaultValue = "10") int limit,
            final @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        logger.info(getClass(), "Request for pending todos: {}", userId);
        ValidationUtils.checkForNullOrInvalidId(userId, NULL_OR_INVALID_USER_ID);

        final var response = this.todoService.getPendingTodosForUser(userId, offset, limit);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> addTodoNote(
            final @RequestBody NewTodoRequest newTodoRequest
    ) {
        logger.info(getClass(), "Request received to add new todo: {}", newTodoRequest);
        ValidationUtils.validateNewTodoRequest(newTodoRequest);

        final var insertedTodo = this.todoService.insert(newTodoRequest);

        return ResponseUtils.generateTodoApiResponse(TODO_CREATED_SUCCESSFULLY, insertedTodo, 201);
    }

    @DeleteMapping(value = "/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> removeTodoNote(
            final @PathVariable String todoId
    ) {
        logger.info(getClass(), "Request received to remove todo with todoId: {}", todoId);
        ValidationUtils.checkForNullOrInvalidId(todoId, NULL_OR_INVALID_TODO_ID);

        this.todoService.delete(todoId);

        return ResponseUtils.generateTodoApiResponse("Todo deleted successfully", null, 200);
    }

    @PutMapping(value = "/todo/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> updateTodo(
            final @RequestBody UpdateTodoRequest updateTodoRequest,
            final @PathVariable String todoId
    ) {
        logger.info(getClass(), "Request received to update todo with todoId: {}", todoId);
        ValidationUtils.checkForNullOrInvalidId(todoId, NULL_OR_INVALID_TODO_ID);
        ValidationUtils.validateUpdateTodoRequest(updateTodoRequest);

        final var updatedTodo = this.todoService.update(todoId, updateTodoRequest);

        return ResponseUtils.generateTodoApiResponse(TODO_UPDATED_SUCCESSFULLY, updatedTodo, 200);
    }

    @PatchMapping(value = "/todo/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoApiResponse> updateTodoStatus(
            @PathVariable final String todoId,
            @RequestBody final UpdateTodoStatusRequest request
    ) {
        logger.info(getClass(), "Request received to update todo status with todoId: {}", todoId);
        ValidationUtils.checkForNullOrInvalidId(todoId, NULL_OR_INVALID_TODO_ID);
        ValidationUtils.validateUpdateTodoStatusRequest(request);

        final var updatedTodo = this.todoService.updateTodoStatus(todoId, request);

        return ResponseUtils.generateTodoApiResponse(TODO_STATUS_UPDATED_SUCCESSFULLY, updatedTodo, 200);
    }

}
