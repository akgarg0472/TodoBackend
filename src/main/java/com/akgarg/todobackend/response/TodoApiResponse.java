package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Getter
@Setter
@ToString
public class TodoApiResponse implements Serializable {

    private static final long serialVersionUID = -89267447546L;

    private String message;
    private Object data;
    private boolean success;
    private int status;
    private long timestamp;

}
