package com.akgarg.todobackend.config.security.jwt;

import com.akgarg.todobackend.response.ApiErrorResponse;
import com.akgarg.todobackend.utils.DateTimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Akhilesh Garg
 * GitHub:
 * <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    )
            throws IOException {
        exception.printStackTrace();

        final ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setErrorMessage("Please login to access requested resource");
        errorResponse.setErrorCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        final String errorJsonString = new ObjectMapper().writeValueAsString(errorResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final PrintWriter out = response.getWriter();
        out.print(errorJsonString);
        out.flush();
    }

}
