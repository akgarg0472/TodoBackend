package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 17-07-2022
 */
@Getter
@Setter
@ToString
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String avatar;

}
