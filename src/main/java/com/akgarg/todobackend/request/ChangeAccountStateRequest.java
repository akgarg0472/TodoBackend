package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 18-09-2022
 */
@Getter
@Setter
public class ChangeAccountStateRequest {

    private String userId;
    private String reason;
    private String by;

}
