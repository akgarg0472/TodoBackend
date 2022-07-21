package com.akgarg.todobackend.service.todo;

import com.akgarg.todobackend.entity.TodoEntity;
import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.request.NewTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoRequest;
import com.akgarg.todobackend.request.UpdateTodoStatusRequest;
import com.akgarg.todobackend.response.PaginatedTodoResponse;
import com.akgarg.todobackend.response.TodoResponseDto;
import com.akgarg.todobackend.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final TodoLogger logger;

    @Override
    public TodoResponseDto insert(NewTodoRequest request) {
        TodoEntity todo = convertRequestDtoToEntity(request);

        todo.setId(ObjectId.get().toString());
        todo.setCompleted(false);
        todo.setDescription(null);
        todo.setCreatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());
        todo.setUpdatedAt(null);

        TodoEntity insertedEntity = todoRepository.insert(todo);

        return convertEntityToDto(insertedEntity);
    }

    @Override
    public void delete(String todoId) {
        TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);

        this.todoRepository.delete(todoEntity);
    }

    @Override
    public TodoResponseDto getTodoById(String todoId) {
        TodoEntity todo = this.getTodoEntityById(todoId, TODO_NOT_FOUND);

        return convertEntityToDto(todo);
    }

    @Override
    public PaginatedTodoResponse getTodosForUser(String userId, int offset, int limit) {
        PaginatedTodoResponse userTodos = this.todoRepository.findAllTodosByUserId(userId, offset, limit);

        if (userTodos == null || userTodos.getTodos() == null || userTodos.getTodos().isEmpty()) {
            throw new TodoException(NO_TODO_FOUND_FOR_USER);
        }

        return userTodos;
    }

    @Override
    public TodoResponseDto update(String todoId, UpdateTodoRequest updateDto) {
        TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);
        todoEntity.setDescription(updateDto.getDescription());
        todoEntity.setTitle(updateDto.getTitle());
        todoEntity.setUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        TodoEntity updatedEntity = todoRepository.save(todoEntity);

        return convertEntityToDto(updatedEntity);
    }

    @Override
    public TodoResponseDto updateTodoStatus(String todoId, UpdateTodoStatusRequest request) {
        TodoEntity todoEntity = this.getTodoEntityById(todoId, TODO_NOT_FOUND_FOR_PROVIDED_ID);
        todoEntity.setCompleted(request.getCompleted());
        todoEntity.setUpdatedAt(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        TodoEntity updatedEntity = todoRepository.save(todoEntity);

        return convertEntityToDto(updatedEntity);
    }

    @Override
    public void removeAllTodoByUserId(String userId) {
        logger.warn(getClass(), "Deleting all todos for userId {}", userId);

        List<TodoEntity> todos = this.todoRepository.findAllByUserId(userId)
                .orElseThrow(() -> new TodoException(NO_TODO_FOUND_FOR_USER));

        logger.warn(getClass(), "Deleted todos for {}: {}", userId, todos);

        this.todoRepository.deleteAll(todos);
    }

    private TodoEntity getTodoEntityById(String todoId, String exceptionMessage) {
        return this.todoRepository.findById(todoId).orElseThrow(() -> new TodoException(exceptionMessage));
    }

    private TodoResponseDto convertEntityToDto(TodoEntity entity) {
        return modelMapper.map(entity, TodoResponseDto.class);
    }

    private TodoEntity convertRequestDtoToEntity(NewTodoRequest dto) {
        return modelMapper.map(dto, TodoEntity.class);
    }

}
