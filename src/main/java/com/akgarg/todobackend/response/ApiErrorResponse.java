package com.akgarg.todobackend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 29-07-2022
 */
@Getter
@Setter
@ToString
public class ApiErrorResponse implements Serializable {

    private static final long serialVersionUID = -872385468L;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("timestamp")
    private long timestamp;

}
