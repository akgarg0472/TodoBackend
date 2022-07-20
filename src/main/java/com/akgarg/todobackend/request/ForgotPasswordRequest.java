package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 20-07-2022
 */
@Getter
@Setter
@ToString
public class ForgotPasswordRequest implements Serializable {

    private static final long serialVersionUID = -9365744534L;

    private String forgotPasswordToken;
    private String password;
    private String confirmPassword;

}
