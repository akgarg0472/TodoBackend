package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 17-07-2022
 */
@Getter
@Setter
@ToString
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 297587284585L;

    private String authToken;
    private String role;
    private String userId;
    private String email;
    private String name;
    private Long timestamp;

}
