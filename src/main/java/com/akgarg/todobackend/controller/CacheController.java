package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.model.request.ConfigPropUpdateRequest;
import com.akgarg.todobackend.service.cache.CacheService;
import com.akgarg.todobackend.utils.ResponseUtils;
import com.akgarg.todobackend.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.CACHE_RELOAD_FAILED;
import static com.akgarg.todobackend.constants.ApplicationConstants.CACHE_RELOAD_SUCCESS;

/**
 * @author Akhilesh Garg
 * @since 19-09-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cache")
public class CacheController {

    private final CacheService cacheService;
    private final ApplicationLogger logger;

    @GetMapping("/reloadCache")
    public ResponseEntity<Map<String, Object>> reloadCache() {
        this.logger.info(getClass(), "Reload cache request received");

        final boolean cacheReloaded = this.cacheService.reloadCache();

        return ResponseUtils.generateBooleanConditionalResponse(
                cacheReloaded,
                CACHE_RELOAD_SUCCESS,
                CACHE_RELOAD_FAILED
        );
    }

    @GetMapping("/getConfigs")
    public ResponseEntity<Map<String, Object>> getConfigProps() {
        this.logger.info(getClass(), "getConfigProps cache request received");

        final var configProps = this.cacheService.getConfigProps();

        return ResponseUtils.generateGetCacheResponse(configProps);
    }

    @GetMapping("/getConfigPropsKeys")
    public List<String> getConfigPropKeys() {
        return this.cacheService.getConfigPropsKeys();
    }

    @GetMapping("/getCache")
    public ResponseEntity<Map<String, Object>> getCache() {
        this.logger.info(getClass(), "getCache cache request received");

        final var cache = this.cacheService.getCache();

        return ResponseUtils.generateGetCacheResponse(cache);
    }

    @PostMapping("/cacheConfig")
    public ResponseEntity<Map<String, Object>> updateConfigProp(final @RequestBody ConfigPropUpdateRequest request) {
        this.logger.info(getClass(), "updateConfigProp cache request received: {}", request);
        ValidationUtils.validateConfigPropUpdateRequest(request);

        final var cacheKVResponse = this.cacheService
                .updateConfigProp(request.getKey(), request.getValue());

        return ResponseUtils.generateUpdateConfigPropResponse(cacheKVResponse);
    }

}
