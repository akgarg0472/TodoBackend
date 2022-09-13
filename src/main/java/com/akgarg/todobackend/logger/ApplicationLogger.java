package com.akgarg.todobackend.logger;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@SuppressWarnings("unused")
public interface ApplicationLogger {

    void log(
            Class<?> className, TodoLogLevel logLevel, String message, Object... params
    );

    default void all(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.ALL, message, params);
    }

    default void trace(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.TRACE, message, params);
    }

    default void debug(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.DEBUG, message, params);
    }

    default void info(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.INFO, message, params);
    }

    default void warn(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.WARN, message, params);
    }

    default void error(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.ERROR, message, params);
    }

    default void fatal(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.FATAL, message, params);
    }

    default void off(Class<?> className, String message, Object... params) {
        this.log(className, TodoLogLevel.OFF, message, params);
    }

}
