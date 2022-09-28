package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 23-07-2022
 */
@Getter
@Setter
@ToString
public class ChangePasswordRequest {

    private String oldPassword;
    private String password;
    private String confirmPassword;

}
