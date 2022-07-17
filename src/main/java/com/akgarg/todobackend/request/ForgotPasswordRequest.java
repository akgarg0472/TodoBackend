package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 17-07-2022
 */
@Getter
@Setter
@ToString
public class ForgotPasswordRequest {

    private String email;

}