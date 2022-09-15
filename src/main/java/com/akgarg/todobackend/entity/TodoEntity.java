package com.akgarg.todobackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
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
