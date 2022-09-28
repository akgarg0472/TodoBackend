package com.akgarg.todobackend.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Component
public final class ApplicationLoggerImpl implements ApplicationLogger {

    private final Map<TodoLogLevel, Level> levelMap;

    public ApplicationLoggerImpl() {
        this.levelMap = new HashMap<>();
        this.initLogMap();
    }

    private void initLogMap() {
        levelMap.put(TodoLogLevel.ALL, Level.ALL);
        levelMap.put(TodoLogLevel.TRACE, Level.TRACE);
        levelMap.put(TodoLogLevel.DEBUG, Level.DEBUG);
        levelMap.put(TodoLogLevel.INFO, Level.INFO);
        levelMap.put(TodoLogLevel.WARN, Level.WARN);
        levelMap.put(TodoLogLevel.ERROR, Level.ERROR);
        levelMap.put(TodoLogLevel.FATAL, Level.FATAL);
    }

    @Override
    public void log(
            final Class<?> className, final TodoLogLevel logLevel, final String message, final Object... params
    ) {
        final var pMessage = new ParameterizedMessage(className.getSimpleName() + " -> " + message, params, null);
        LogManager.getLogger(className).log(levelMap.get(logLevel), pMessage);
    }

}
