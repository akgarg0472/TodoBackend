package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 18-09-2022
 */
@SuppressWarnings("unused")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(final Throwable cause) {
        super(cause);
    }

    public BadRequestException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
