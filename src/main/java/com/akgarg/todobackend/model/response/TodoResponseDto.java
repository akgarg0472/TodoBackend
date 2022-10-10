package com.akgarg.todobackend.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Getter
@Setter
@ToString
public class TodoResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8972830008465468L;

    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Long createdAt;
    private Long updatedAt;

}
