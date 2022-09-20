package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.response.PaginatedUserResponse;
import com.akgarg.todobackend.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Repository
public interface UserRepository extends MongoRepository<TodoUser, String> {

    default PaginatedUserResponse getAllUsers(int offset, int limit) {
        if (limit <= 0) {
            limit = 10;
        } else if (limit > 20) {
            limit = 20;
        }

        if (offset < 0) {
            offset = 0;
        }

        Pageable pageable = PageRequest.of(offset, limit);
        Page<TodoUser> entities = this.findAll(pageable);

        return getPaginatedUserResponse(entities);
    }

    private PaginatedUserResponse getPaginatedUserResponse(final Page<TodoUser> entities) {
        long totalUsers = entities.getTotalElements();
        int totalPages = entities.getTotalPages();
        int currentPage = entities.getNumber();

        List<UserResponseDto> users = entities.getContent()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

        return new PaginatedUserResponse(currentPage, totalUsers, totalPages, users);
    }

    Optional<TodoUser> findByEmail(String email);

    Optional<TodoUser> findByIdAndEmail(String id, String email);

}
