package com.akgarg.todobackend.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Getter
@Setter
public class RegisterUserRequest {

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
    
}
