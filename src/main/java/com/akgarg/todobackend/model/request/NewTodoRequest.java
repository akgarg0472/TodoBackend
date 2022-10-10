package com.akgarg.todobackend.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Getter
@Setter
@ToString
public class NewTodoRequest {

    private String userId;
    private String title;
    private String description;
    private boolean completed;

}

