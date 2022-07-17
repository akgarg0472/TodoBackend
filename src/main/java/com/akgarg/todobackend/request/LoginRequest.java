package com.akgarg.todobackend.request;

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
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -37786374678347684L;

    private String email;
    private String password;

}
