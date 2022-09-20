package com.akgarg.todobackend.response;

import com.akgarg.todobackend.entity.TodoUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class UserResponseDto implements Serializable {

    private static final long serialVersionUID = -668638513628765L;

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private Long createdAt;
    private Long lastUpdatedAt;

    public UserResponseDto(TodoUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.avatar = user.getAvatar();
        this.createdAt = user.getCreatedAt();
        this.lastUpdatedAt = user.getLastUpdatedAt();
    }

}
