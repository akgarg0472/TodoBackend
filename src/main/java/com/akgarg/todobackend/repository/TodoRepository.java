package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.adapter.TodoEntityToTodoResponseDtoAdapter;
import com.akgarg.todobackend.model.entity.TodoEntity;
import com.akgarg.todobackend.model.response.PaginatedTodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Repository
public interface TodoRepository extends MongoRepository<TodoEntity, String> {

    Optional<List<TodoEntity>> findAllByUserId(final String userId);

    Page<TodoEntity> findAllByUserId(final String userId, final Pageable pageable);

    Page<TodoEntity> findAllByUserIdAndCompletedTrue(final String userId, final Pageable pageable);

    Page<TodoEntity> findAllByUserIdAndCompletedFalse(final String userId, final Pageable pageable);

    default PaginatedTodoResponse findAllTodosByUserId(final String userId, int offset, int limit) {
        if (limit <= 0) {
            limit = 10;
        } else if (limit > 20) {
            limit = 20;
        }

        if (offset < 0) {
            offset = 0;
        }

        final var pageable = PageRequest.of(offset, limit);
        final var entities = this.findAllByUserId(userId, pageable);

        return getPaginatedTodoResponse(entities);
    }

    default PaginatedTodoResponse findCompletedTodosByUserId(final String userId, int offset, int limit) {
        if (limit <= 0) {
            limit = 10;
        } else if (limit > 20) {
            limit = 20;
        }

        if (offset < 0) {
            offset = 0;
        }

        final var pageable = PageRequest.of(offset, limit);
        final var entities = this.findAllByUserIdAndCompletedTrue(userId, pageable);

        return getPaginatedTodoResponse(entities);
    }

    default PaginatedTodoResponse findPendingTodosByUserId(final String userId, int offset, int limit) {
        if (limit <= 0) {
            limit = 10;
        } else if (limit > 20) {
            limit = 20;
        }

        if (offset < 0) {
            offset = 0;
        }

        final var pageable = PageRequest.of(offset, limit);
        final var entities = this.findAllByUserIdAndCompletedFalse(userId, pageable);

        return getPaginatedTodoResponse(entities);
    }

    private PaginatedTodoResponse getPaginatedTodoResponse(final Page<TodoEntity> entities) {
        final long totalTodos = entities.getTotalElements();
        final int totalPages = entities.getTotalPages();
        final int currentPage = entities.getNumber();

        final var todos = entities.getContent()
                .stream()
                .map(TodoEntityToTodoResponseDtoAdapter::convert)
                .collect(Collectors.toList());

        return new PaginatedTodoResponse(currentPage, totalTodos, totalPages, todos);
    }

}
