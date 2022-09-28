package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.adapter.TodoUserToUserResponseDtoAdapter;
import com.akgarg.todobackend.dto.AdminDashboardUserInfoDto;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.response.PaginatedUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
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

        final PageRequest pageable = PageRequest.of(offset, limit);
        final var entities = this.findAll(pageable);

        return getPaginatedUserResponse(entities);
    }

    private PaginatedUserResponse getPaginatedUserResponse(final Page<TodoUser> entities) {
        final long totalUsers = entities.getTotalElements();
        final int totalPages = entities.getTotalPages();
        final int currentPage = entities.getNumber();

        final var users = entities.getContent()
                .stream()
                .map(TodoUserToUserResponseDtoAdapter::convert)
                .collect(Collectors.toList());

        return new PaginatedUserResponse(currentPage, totalUsers, totalPages, users);
    }

    Optional<TodoUser> findByEmail(final String email);

    Optional<TodoUser> findByIdAndEmail(final String id, final String email);

    List<AdminDashboardUserInfoDto> findAllBy();

    default List<AdminDashboardUserInfoDto> getAdminDashboardInfo() {
        return this.findAllBy();
    }

}
