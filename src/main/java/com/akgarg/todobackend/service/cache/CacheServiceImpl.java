package com.akgarg.todobackend.service.cache;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.exception.BadRequestException;
import com.akgarg.todobackend.exception.GenericException;
import com.akgarg.todobackend.model.entity.TodoConfig;
import com.akgarg.todobackend.model.response.CacheKVResponse;
import com.akgarg.todobackend.repository.ConfigRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.INTERNAL_SERVER_ERROR;
import static com.akgarg.todobackend.constants.ApplicationConstants.INVALID_CONFIG_PROPS_KEY;

/**
 * @author Akhilesh Garg
 * @since 28-09-2022
 */
@AllArgsConstructor
@Service
public class CacheServiceImpl implements CacheService {

    private final ApplicationCache cache;
    private final ConfigRepository configRepository;

    @Override
    public List<CacheKVResponse> getCache() {
        final var cacheMap = this.cache.getCacheMap();
        return generateResponseFromCacheMap(cacheMap);
    }

    @Override
    public List<CacheKVResponse> getConfigProps() {
        final var configsMap = this.cache.getConfigsMap();
        return generateResponseFromCacheMap(configsMap);
    }

    @Override
    public boolean reloadCache() {
        this.cache.reloadCache();
        return true;
    }

    @Override
    public CacheKVResponse updateConfigProp(
            final String _key,
            final String value
    ) {
        try {
            final var key = CacheConfigKey.valueOf(_key);
            final var config = new TodoConfig(key.name(), value);

            final var updatedConfigProp = this.configRepository.save(config);
            this.cache.insertOrUpdateConfig(updatedConfigProp.getKey(), updatedConfigProp.getValue());

            return new CacheKVResponse(updatedConfigProp.getKey(), updatedConfigProp.getValue());
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw new BadRequestException(INVALID_CONFIG_PROPS_KEY, e);
            }

            throw new GenericException(INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public List<String> getConfigPropsKeys() {
        final List<String> configPropKeys = new ArrayList<>();

        for (final CacheConfigKey key : CacheConfigKey.values()) {
            configPropKeys.add(key.name());
        }

        return configPropKeys;
    }

    @Override
    public String getConfig(
            final CacheConfigKey key,
            final String defaultValue
    ) {
        return this.cache.getConfigValue(key.name(), defaultValue);
    }

    private List<CacheKVResponse> generateResponseFromCacheMap(final Map<String, ?> cacheMap) {
        final List<CacheKVResponse> response = new ArrayList<>();
        cacheMap.forEach((key, value) -> response.add(new CacheKVResponse(key, value)));
        return response;
    }

}
