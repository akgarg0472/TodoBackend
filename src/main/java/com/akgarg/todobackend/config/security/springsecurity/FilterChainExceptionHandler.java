package com.akgarg.todobackend.config.security.springsecurity;

import com.akgarg.todobackend.exception.UserException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 17-07-2022
 */
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public FilterChainExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            switch (e.getClass().getSimpleName()) {
                case "SignatureException":
                    resolver.resolveException(request, response, null, new UserException(UNKNOWN_JWT_TOKEN));
                    break;

                case "ExpiredJwtException":
                    resolver.resolveException(request, response, null, new UserException(EXPIRED_JWT_TOKEN));
                    break;

                case "MalformedJwtException":
                    resolver.resolveException(request, response, null, new UserException(INVALID_JWT_TOKEN));
                    break;

                default:
                    resolver.resolveException(request, response, null, e);
            }
        }
    }

}
