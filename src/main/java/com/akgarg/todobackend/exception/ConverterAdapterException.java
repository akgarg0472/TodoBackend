package com.akgarg.todobackend.exception;

/**
 * @author Akhilesh Garg
 * @since 03-10-2022
 */
@SuppressWarnings("unused")
public class ConverterAdapterException extends RuntimeException {

    public ConverterAdapterException() {
        super();
    }

    public ConverterAdapterException(final String message) {
        super(message);
    }

    public ConverterAdapterException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
