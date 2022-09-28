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
public class ForgotPasswordEmailRequest {

    private String email;

}
