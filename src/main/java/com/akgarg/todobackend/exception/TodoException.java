package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("unused")
public class TodoException extends RuntimeException {

    public TodoException() {
    }

    public TodoException(final String message) {
        super(message);
    }

    public TodoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TodoException(final Throwable cause) {
        super(cause);
    }

    public TodoException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
