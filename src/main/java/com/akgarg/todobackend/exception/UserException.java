package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("unused")
public class UserException extends RuntimeException {

    public UserException() {
    }

    public UserException(final String message) {
        super(message);
    }

    public UserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserException(final Throwable cause) {
        super(cause);
    }

    public UserException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
