package com.akgarg.todobackend.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 20-07-2022
 */
@Getter
@Setter
public class ForgotPasswordRequest {

    private String forgotPasswordToken;
    private String password;
    private String confirmPassword;

}
