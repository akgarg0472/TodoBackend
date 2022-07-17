package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.config.email.EmailSenderConfigProperties;
import com.akgarg.todobackend.exception.TodoException;
import com.akgarg.todobackend.request.SendEmailRequest;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 14-07-2022
 */
public class EmailUtils {

    public static void checkSendEmailRequest(SendEmailRequest request) {
        if (request.getToEmail() == null || request.getToEmail().trim().isEmpty()) {
            throw new TodoException("Invalid 'to' email in email send request");
        }

        if (request.getSubject() == null || request.getSubject().trim().isEmpty()) {
            throw new TodoException("Invalid email 'subject' in email send request");
        }

        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new TodoException("Invalid email 'message' in email send request");
        }

    }

    public static void checkEmailSenderProperties(EmailSenderConfigProperties config) {
        if (config == null || config.getHost() == null || config.getPort() == 0 || config.getSenderEmail() == null || config.getSenderEmailPassword() == null) {
            throw new NullPointerException("Invalid email config properties");
        }
    }

}
