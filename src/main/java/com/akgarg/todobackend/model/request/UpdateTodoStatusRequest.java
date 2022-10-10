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
public class UpdateTodoStatusRequest {

    private Boolean completed;

}
