package com.akgarg.todobackend.adapter;

import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.response.UserResponseDto;

/**
 * @author Akhilesh Garg
 * @since 29-09-2022
 */
public class TodoUserToUserResponseDtoAdapter {

    public static UserResponseDto convert(final TodoUser entity) {
        final var dto = new UserResponseDto();

        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAvatar(entity.getAvatar());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastUpdatedAt(entity.getLastUpdatedAt());

        return dto;
    }

}
