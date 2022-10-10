package com.akgarg.todobackend.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 30-07-2022
 */
@Getter
@Setter
@ToString
public class SignupResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -3578346457L;

    private String message;
    private Integer status;
    private Long timestamp;

}
