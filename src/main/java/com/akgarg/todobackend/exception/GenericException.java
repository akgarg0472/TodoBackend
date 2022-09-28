package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("unused")
public class GenericException extends RuntimeException {

    public GenericException() {
    }

    public GenericException(final String message) {
        super(message);
    }

    public GenericException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GenericException(final Throwable cause) {
        super(cause);
    }

    public GenericException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
