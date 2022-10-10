package com.akgarg.todobackend.service.cache;

import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.model.response.CacheKVResponse;

import java.util.List;

/**
 * @author Akhilesh Garg
 * @since 28-09-2022
 */
public interface CacheService {

    List<CacheKVResponse> getCache();

    List<CacheKVResponse> getConfigProps();

    boolean reloadCache();

    CacheKVResponse updateConfigProp(final String key, final String value);

    List<String> getConfigPropsKeys();

    String getConfig(final CacheConfigKey key, final String defaultValue);

}
