package com.akgarg.todobackend.logger;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("unused")
public interface ApplicationLogger {

    void log(
            final Class<?> className, final TodoLogLevel logLevel, final String message, final Object... params
    );

    default void all(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.ALL, message, params);
    }

    default void trace(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.TRACE, message, params);
    }

    default void debug(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.DEBUG, message, params);
    }

    default void info(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.INFO, message, params);
    }

    default void warn(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.WARN, message, params);
    }

    default void error(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.ERROR, message, params);
    }

    default void fatal(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.FATAL, message, params);
    }

    default void off(final Class<?> className, final String message, final Object... params) {
        this.log(className, TodoLogLevel.OFF, message, params);
    }

}
