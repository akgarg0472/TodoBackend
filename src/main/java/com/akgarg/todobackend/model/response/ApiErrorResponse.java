package com.akgarg.todobackend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 29-07-2022
 */
@Getter
@Setter
@ToString
public class ApiErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -872385468L;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("timestamp")
    private Long timestamp;

}
