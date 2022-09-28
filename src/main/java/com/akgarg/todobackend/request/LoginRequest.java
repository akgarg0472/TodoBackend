package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 17-07-2022
 */
@Getter
@Setter
@ToString
public class LoginRequest {

    private String email;
    private String password;

}
