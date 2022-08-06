package com.akgarg.todobackend.config.security.jwt;

import com.akgarg.todobackend.utils.DateTimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException {
        Map<String, Object> errorsMap = new HashMap<>();

        errorsMap.put("error_message", "Please login to access requested resource");
        errorsMap.put("error_status", HttpServletResponse.SC_UNAUTHORIZED);
        errorsMap.put("success", false);
        errorsMap.put("timestamp", DateTimeUtils.getCurrentDateTimeInMilliseconds());

        String errorJsonString = new ObjectMapper().writeValueAsString(errorsMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter out = response.getWriter();
        out.print(errorJsonString);
        out.flush();
    }

}
