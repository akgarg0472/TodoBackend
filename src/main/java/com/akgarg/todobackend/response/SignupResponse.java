package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 30-07-2022
 */
@Getter
@Setter
@ToString
public class SignupResponse implements Serializable {

    private static final long serialVersionUID = -3578346457L;

    private String message;
    private int status;
    private long timestamp;

}
