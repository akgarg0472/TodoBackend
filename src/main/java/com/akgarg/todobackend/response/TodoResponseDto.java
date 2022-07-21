package com.akgarg.todobackend.response;

import com.akgarg.todobackend.entity.TodoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoResponseDto {

    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Long createdAt;
    private Long updatedAt;

    public TodoResponseDto(TodoEntity todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.completed = todo.isCompleted();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }

}
