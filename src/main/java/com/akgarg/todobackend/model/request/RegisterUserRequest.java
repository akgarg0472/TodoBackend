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
public class RegisterUserRequest {

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;

}
