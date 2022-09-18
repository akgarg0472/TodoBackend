package com.akgarg.todobackend.cache;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Component
@SuppressWarnings("unused")
public class TodoUserCache {

    private final Map<String, Object> cacheMap;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private ApplicationLogger logger;

    public TodoUserCache() {
        this.cacheMap = new HashMap<>();
    }

    public void insertKeyValue(String key, Object value) {
        logger.info(getClass(), "Inserting {} -> {} into cache", key, value);
        ValidationUtils.checkForNullOrInvalidValue(key);
        ValidationUtils.checkForNullOrInvalidValue(value);

        this.cacheMap.put(key, value);
    }

    public Optional<Object> getValue(String key) {
        logger.info(getClass(), "Fetching value of {} from cache", key);

        Object value = this.cacheMap.get(key);

        return Optional.ofNullable(value);
    }

    public boolean removeValue(String key) {
        logger.info(getClass(), "Removing {} entry from cache", key);

        ValidationUtils.checkForNullOrInvalidValue(key);
        Object value = this.cacheMap.remove(key);

        return value != null;
    }

    public void clearCache() {
        logger.warn(getClass(), "Clearing cache memory");

        this.cacheMap.clear();
    }

}
