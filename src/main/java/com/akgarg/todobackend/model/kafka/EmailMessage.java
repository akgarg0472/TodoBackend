package com.akgarg.todobackend.model.kafka;

import lombok.Data;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class EmailMessage {

    private String[] recipients;
    private String subject;
    private String message;

}
