package com.akgarg.todobackend.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Component
public class ApplicationLoggerImpl implements ApplicationLogger {

    private final Map<TodoLogLevel, Level> levelMap;

    public ApplicationLoggerImpl() {
        levelMap = new HashMap<>();
        initLogMap();
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
    public void log(Class<?> className, TodoLogLevel logLevel, String message, Object... params) {
        ParameterizedMessage pMessage = new ParameterizedMessage(className.getSimpleName() + " -> " + message, params, null);
        LogManager.getLogger(className).log(levelMap.get(logLevel), pMessage);
    }

}
