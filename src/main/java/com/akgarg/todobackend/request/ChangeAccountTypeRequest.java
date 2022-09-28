package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 18-09-2022
 */
@Getter
@Setter
@ToString
public class ChangeAccountTypeRequest {

    private String userId;
    private String accountType;
    private String by;

}
