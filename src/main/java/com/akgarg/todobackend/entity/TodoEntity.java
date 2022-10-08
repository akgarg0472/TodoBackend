package com.akgarg.todobackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Document("todos")
@Getter
@Setter
@ToString
public class TodoEntity {

    @Id
    private String id;

    private String userId;
    private String title;
    private String description;
    private Boolean completed;

    private Long createdAt;
    private Long updatedAt;

}
