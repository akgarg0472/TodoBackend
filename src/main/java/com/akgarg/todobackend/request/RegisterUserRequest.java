package com.akgarg.todobackend.request;

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
public class RegisterUserRequest implements Serializable {

    private static final long serialVersionUID = -187394576967L;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
