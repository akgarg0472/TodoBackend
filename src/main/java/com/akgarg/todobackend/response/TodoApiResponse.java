package com.akgarg.todobackend.response;

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
public class TodoApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -89267447546L;

    private String message;
    private Object data;
    private Boolean success;
    private Integer status;
    private Long timestamp;

}
