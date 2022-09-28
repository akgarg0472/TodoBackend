package com.akgarg.todobackend.config.todo;

import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.service.cache.CacheService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Configuration
@AllArgsConstructor
public class TodoConfig implements WebMvcConfigurer {

    private final CacheService cacheService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        final String[] requestMethods = {"GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"};
        WebMvcConfigurer.super.addCorsMappings(registry);

        final String frontendBaseUrl = this.cacheService
                .getConfig(CacheConfigKey.FRONTEND_BASE_URL, "http://localhost:3000");

        registry.addMapping("/**")
                .allowedOrigins(frontendBaseUrl)
                .allowedMethods(requestMethods);
    }

}
