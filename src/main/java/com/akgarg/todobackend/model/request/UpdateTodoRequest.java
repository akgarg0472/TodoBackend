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
public class UpdateTodoRequest {

    private String title;
    private String description;
    private Boolean completed;

}
