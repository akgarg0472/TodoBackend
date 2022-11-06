package com.akgarg.todobackend.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 17-07-2022
 */
@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                '}';
    }

}
