package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Getter
@Setter
@ToString
public class UserResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -668638513628765L;

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private Long createdAt;
    private Long lastUpdatedAt;

}
