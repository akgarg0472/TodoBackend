package com.akgarg.todobackend.adapter;

import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.exception.ConvertAdapterException;
import com.akgarg.todobackend.response.TodoResponseDto;
import com.akgarg.todobackend.response.UserResponseDto;

import static com.akgarg.todobackend.constants.ApplicationConstants.ROLE_ADMIN;

/**
 * @author Akhilesh Garg
 * @since 29-09-2022
 */
public class TodoUserToUserResponseDtoAdapter {

    public static UserResponseDto convert(final TodoUser entity) {
        try {
            final var dto = new UserResponseDto();

            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setFirstName(entity.getFirstName());
            dto.setLastName(entity.getLastName());
            dto.setAvatar(entity.getAvatar());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setLastUpdatedAt(entity.getLastUpdatedAt());
            dto.setRole(getRole(entity.getRole()));

            return dto;
        } catch (Exception e) {
            throw new ConvertAdapterException(getErrorMessage(entity), e);
        }
    }

    private static String getRole(final String role) {
        return ROLE_ADMIN.equalsIgnoreCase(role) ? "Admin" : "User";
    }

    private static String getErrorMessage(final Object entity) {
        try {
            return "Error converting " + entity.getClass().getName() + " instance to " + TodoResponseDto.class.getName();
        } catch (Exception e) {
            return "Error converting " + entity;
        }
    }

}
