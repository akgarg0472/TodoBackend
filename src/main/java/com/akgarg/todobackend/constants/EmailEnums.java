package com.akgarg.todobackend.constants;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
public enum EmailEnums {

    TRANSPORT_PROTOCOL("mail.transport.protocol"),
    SMTP_AUTH("mail.smtp.auth"),
    SMTP_START_TLS_ENABLED("mail.smtp.starttls.enable"),
    DEBUG("mail.debug");

    private final String value;

    EmailEnums(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
