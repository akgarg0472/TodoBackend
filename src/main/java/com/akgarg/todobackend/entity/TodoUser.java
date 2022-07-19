package com.akgarg.todobackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Document("users")
@Getter
@Setter
@ToString
public class TodoUser {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String avatar;

    private Boolean isEnabled;
    private Boolean isAccountNonLocked;

    private String accountVerificationToken;
    private String forgotPasswordToken;

    private Long createdAt;
    private Long lastUpdatedAt;

}
