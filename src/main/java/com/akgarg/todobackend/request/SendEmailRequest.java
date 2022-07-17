package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 14-07-2022
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class SendEmailRequest {

    private String toEmail;
    private String message;
    private String subject;

}
