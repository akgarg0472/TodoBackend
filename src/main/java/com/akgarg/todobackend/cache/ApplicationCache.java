package com.akgarg.todobackend.cache;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.ConfigRepository;
import com.akgarg.todobackend.utils.ValidationUtils;
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
public class ApplicationCache {

    private final ApplicationLogger logger;
    private final ConfigRepository configRepository;

    private final Map<String, Object> cacheMap;
    private final Map<String, String> configsMap;

    public ApplicationCache(final ApplicationLogger logger, final ConfigRepository configRepository) {
        this.logger = logger;
        this.configRepository = configRepository;

        this.cacheMap = new HashMap<>();
        this.configsMap = new HashMap<>();

        initConfigsMap();
    }

    private void initConfigsMap() {
        try {
            this.configRepository.findAll().forEach(config -> this.addConfig(config.getKey(), config.getValue()));
            this.logger.info(getClass(), "Config props loaded successfully. Loaded {} props", this.configsMap.size());
        } catch (Exception e) {
            this.logger.error(getClass(), "Error fetching configs from DB: {}", e.getMessage());
        }
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
        this.configsMap.clear();
    }

    public void reloadCache() {
        initConfigsMap();
        logger.info(getClass(), "Cache reload success");
    }

    public void addConfig(String key, String value) {
        this.configsMap.put(key, value);
    }

    public String getConfig(final String propName, final String defaultValue) {
        logger.info(getClass(), "Fetching value of {} from configs cache", propName);

        return this.configsMap.getOrDefault(propName, defaultValue);
    }

}
