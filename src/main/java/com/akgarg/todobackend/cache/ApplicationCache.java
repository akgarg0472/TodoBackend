package com.akgarg.todobackend.cache;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.ConfigRepository;
import com.akgarg.todobackend.utils.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Component
public  class ApplicationCache {

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
            this.configRepository
                    .findAll()
                    .forEach(config -> this.insertOrUpdateConfig(config.getKey(), config.getValue()));

            this.logger.info(getClass(), "Config props loaded successfully. Loaded {} props", this.configsMap.size());
        } catch (Exception e) {
            this.logger.error(getClass(), "Error fetching configs from DB: {}", e.getMessage());
        }
    }

    public void insertOrUpdateUserKeyValue(final String key, final Object value) {
        logger.info(getClass(), "Inserting/Updating {} -> {} into cache", key, value);
        ValidationUtils.checkForNullOrInvalidValue(key);
        ValidationUtils.checkForNullOrInvalidValue(value);

        this.cacheMap.put(key, value);
    }

    public Optional<Object> getValue(final String key) {
        logger.info(getClass(), "Fetching value of {} from cache", key);

        final Object value = this.cacheMap.get(key);

        return Optional.ofNullable(value);
    }

    public void clearCache() {
        this.logger.warn(getClass(), "Clearing cache memory");
        this.cacheMap.clear();
        this.configsMap.clear();
    }

    public void reloadCache() {
        this.clearCache();
        this.initConfigsMap();
        logger.info(getClass(), "Cache reload success");
    }

    public void insertOrUpdateConfig(final String key, final String value) {
        this.configsMap.put(key, value);
    }

    public String getConfigValue(final String propName, final String defaultValue) {
        logger.info(getClass(), "Fetching value of {} from configs cache", propName);

        return this.configsMap.getOrDefault(propName, defaultValue);
    }

    public Map<String, Object> getCacheMap() {
        return this.cacheMap;
    }

    public Map<String, String> getConfigsMap() {
        return this.configsMap;
    }

}
