package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 20-07-2022
 */
@Getter
@Setter
@ToString
public class ForgotPasswordRequest {

    private String forgotPasswordToken;
    private String password;
    private String confirmPassword;

}
