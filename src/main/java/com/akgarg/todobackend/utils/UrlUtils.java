package com.akgarg.todobackend.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
public final class UrlUtils {

    private UrlUtils() {
    }

    public static String getUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
    }


}
