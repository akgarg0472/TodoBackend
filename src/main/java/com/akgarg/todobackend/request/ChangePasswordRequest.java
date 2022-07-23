package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 23-07-2022
 */
@Getter
@Setter
public class ChangePasswordRequest {

    private String oldPassword;
    private String password;
    private String confirmPassword;

}
