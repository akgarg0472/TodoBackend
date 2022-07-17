package com.akgarg.todobackend.exception;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@SuppressWarnings("unused")
public class TodoException extends RuntimeException {

    public TodoException() {
    }

    public TodoException(String message) {
        super(message);
    }

    public TodoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoException(Throwable cause) {
        super(cause);
    }

    public TodoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
