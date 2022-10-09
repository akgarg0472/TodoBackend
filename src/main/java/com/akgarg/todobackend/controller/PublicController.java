package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.service.ip.IpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Akhilesh Garg
 * @since 05-10-2022
 */
@RestController
@AllArgsConstructor
public class PublicController {

    private final IpService ipService;

    @GetMapping("/")
    public Map<String, String> welcome(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();

        response.put("message", "Hello from backend API");
        response.put("method", Objects.requireNonNull(request.getMethod()));
        response.put("IP", ipService.getClientIP(request));

        return response;
    }

    @GetMapping("/health")
    public String health() {
        return "Service is running";
    }

}
