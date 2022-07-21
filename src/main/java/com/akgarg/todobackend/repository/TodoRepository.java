package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.entity.TodoEntity;
import com.akgarg.todobackend.response.PaginatedTodoResponse;
import com.akgarg.todobackend.response.TodoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface TodoRepository extends MongoRepository<TodoEntity, String> {

    Optional<List<TodoEntity>> findAllByUserId(String userId);

    Page<TodoEntity> findAllByUserId(String userId, Pageable pageable);

    default PaginatedTodoResponse findAllTodosByUserId(String userId, int offset, int limit) {
        if (limit <= 0) {
            limit = 10;
        } else if (limit > 20) {
            limit = 20;
        }

        if (offset < 0) {
            offset = 0;
        }

        Pageable pageable = PageRequest.of(offset, limit);
        Page<TodoEntity> entities = this.findAllByUserId(userId, pageable);

        long totalTodos = entities.getTotalElements();
        int totalPages = entities.getTotalPages();
        int currentPage = entities.getNumber();
        List<TodoResponseDto> todos = entities.getContent()
                .stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());

        return new PaginatedTodoResponse(currentPage, totalTodos, totalPages, todos);
    }


}
