package com.akgarg.todobackend.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akhilesh Garg
 * @since 19-07-2022
 */
public final class UrlUtils {

    private UrlUtils() {
    }

    public static String getUrl(final HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
    }

}
