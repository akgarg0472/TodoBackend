package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 17-07-2022
 */
@Getter
@Setter
@ToString
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 297587284585L;

    private String authToken;
    private String role;
    private String userId;
    private String email;
    private String name;
    private long timestamp;

}
