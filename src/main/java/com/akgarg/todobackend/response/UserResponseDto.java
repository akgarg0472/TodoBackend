package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Getter
@Setter
@ToString
public class UserResponseDto implements Serializable {

    private static final long serialVersionUID = -668638513628765L;

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Long createdAt;
    private Long lastUpdatedAt;

}
