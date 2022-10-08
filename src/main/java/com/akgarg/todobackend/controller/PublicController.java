package com.akgarg.todobackend.controller;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Akhilesh Garg
 * @since 05-10-2022
 */
@RestController
public class PublicController {

    @GetMapping()
    public Map<String, String> welcome(RequestEntity<?> request) {
        Map<String, String> response = new HashMap<>();

        response.put("message", "Hello from welcome API");
        response.put("url", request.getUrl().toString());
        response.put("method", Objects.requireNonNull(request.getMethod()).toString());
        response.put("headers", request.getHeaders().toString());

        return response;
    }

    @GetMapping("/health")
    public String health() {
        return "Service is running";
    }

}
