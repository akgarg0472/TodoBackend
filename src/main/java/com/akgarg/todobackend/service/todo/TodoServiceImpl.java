package com.akgarg.todobackend.service.todo;

import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.entity.TodoEntity;
import com.akgarg.todobackend.model.request.NewTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoRequest;
import com.akgarg.todobackend.model.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.model.response.PaginatedTodoResponse;
import com.akgarg.todobackend.model.response.TodoResponseDto;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final ApplicationLogger logger;

    @Override
    public TodoResponseDto insert(final NewTodoRequest request) {
        final TodoEntity todo = convertRequestDtoToEntity(request);

        todo.setId(ObjectId.get().toString());
        todo.setCreatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());
        todo.setUpdatedAt(null);

        final var insertedEntity = todoRepository.insert(todo);

        return convertEntityToDto(insertedEntity);
    }

    @Override
    public void delete(final String todoId) {
        final TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);

        this.todoRepository.delete(todoEntity);
    }

    @Override
    public TodoResponseDto getTodoById(final String todoId) {
        final TodoEntity todo = this.getTodoEntityById(todoId, TODO_NOT_FOUND);

        return convertEntityToDto(todo);
    }

    @Override
    public PaginatedTodoResponse getTodosForUser(
            final String userId,
            final int offset,
            final int limit
    ) {
        final var userTodos = this.todoRepository.findAllTodosByUserId(userId, offset, limit);

        if (userTodos == null || userTodos.getTodos() == null) {
            return PaginatedTodoResponse.emptyResponse();
        }

        return userTodos;
    }

    @Override
    public TodoResponseDto update(
            final String todoId,
            final UpdateTodoRequest updateDto
    ) {
        final TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);

        todoEntity.setDescription(updateDto.getDescription());
        todoEntity.setTitle(updateDto.getTitle());
        todoEntity.setCompleted(updateDto.getCompleted());
        todoEntity.setUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        final TodoEntity updatedEntity = todoRepository.save(todoEntity);

        return convertEntityToDto(updatedEntity);
    }

    @Override
    public TodoResponseDto updateTodoStatus(
            final String todoId,
            final UpdateTodoStatusRequest request
    ) {
        final TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);
        todoEntity.setCompleted(request.getCompleted());
        todoEntity.setUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        final TodoEntity updatedEntity = todoRepository.save(todoEntity);

        return convertEntityToDto(updatedEntity);
    }

    @Override
    public void removeAllTodoByUserId(final String userId) {
        logger.warn(getClass(), "Deleting all todos for userId {}", userId);

        final var todos = this.todoRepository.findAllByUserId(userId).orElseThrow(() -> new TodoException(NO_TODO_FOUND_FOR_USER));

        logger.warn(getClass(), "Deleted todos for {}: {}", userId, todos);

        this.todoRepository.deleteAll(todos);
    }

    @Override
    public PaginatedTodoResponse getCompletedTodosForUser(
            final String userId,
            final int offset,
            final int limit
    ) {
        final var completedTodos = this.todoRepository
                .findCompletedTodosByUserId(userId, offset, limit);

        if (completedTodos == null || completedTodos.getTodos() == null) {
            throw new TodoException(NO_TODO_FOUND_FOR_USER);
        }

        return completedTodos;
    }

    @Override
    public PaginatedTodoResponse getPendingTodosForUser(
            final String userId,
            final int offset,
            final int limit
    ) {
        final var pendingTodos = this.todoRepository
                .findPendingTodosByUserId(userId, offset, limit);

        if (pendingTodos == null || pendingTodos.getTodos() == null) {
            throw new TodoException(NO_TODO_FOUND_FOR_USER);
        }

        return pendingTodos;
    }

    private TodoEntity getTodoEntityById(
            final String todoId,
            final String exceptionMessage
    ) {
        return this.todoRepository
                .findById(todoId)
                .orElseThrow(() -> new TodoException(exceptionMessage));
    }

    private TodoResponseDto convertEntityToDto(final TodoEntity entity) {
        return modelMapper.map(entity, TodoResponseDto.class);
    }

    private TodoEntity convertRequestDtoToEntity(final NewTodoRequest dto) {
        return modelMapper.map(dto, TodoEntity.class);
    }

}
