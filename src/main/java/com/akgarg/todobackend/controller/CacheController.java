package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.cache.ApplicationCache;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-09-2022
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cache")
public class CacheController {

    private final ApplicationCache cache;

    @GetMapping("/reloadCache")
    public void reloadCache() {
        this.cache.reloadCache();
    }

}
