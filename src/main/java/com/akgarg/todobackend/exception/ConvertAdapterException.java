package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 03-10-2022
 */
@SuppressWarnings("unused")
public class ConvertAdapterException extends RuntimeException {

    public ConvertAdapterException() {
        super();
    }

    public ConvertAdapterException(final String message) {
        super(message);
    }

    public ConvertAdapterException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
