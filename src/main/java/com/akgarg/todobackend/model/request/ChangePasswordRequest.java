package com.akgarg.todobackend.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 23-07-2022
 */
@Getter
@Setter
public class ChangePasswordRequest {

    private String oldPassword;
    private String password;
    private String confirmPassword;

}
