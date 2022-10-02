package com.akgarg.todobackend.adapter;

import com.akgarg.todobackend.entity.TodoEntity;
import com.akgarg.todobackend.exception.ConvertAdapterException;
import com.akgarg.todobackend.response.TodoResponseDto;

/**
 * @author Akhilesh Garg
 * @since 29-09-2022
 */
public class TodoEntityToTodoResponseDtoAdapter {

    public static TodoResponseDto convert(final TodoEntity entity) {
        try {
            final var dto = new TodoResponseDto();

            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setCompleted(entity.getCompleted());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());

            return dto;
        } catch (Exception e) {
            throw new ConvertAdapterException(getErrorMessage(entity), e);
        }
    }

    private static String getErrorMessage(final Object entity) {
        try {
            return "Error converting " + entity.getClass().getName() + " instance to " + TodoResponseDto.class.getName();
        } catch (Exception e) {
            return "Error converting ";
        }
    }

}
