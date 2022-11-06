package com.akgarg.todobackend.config.security.jwt;

import com.akgarg.todobackend.cache.JwtTokenBlackList;
import com.akgarg.todobackend.exception.UserException;
import com.akgarg.todobackend.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.akgarg.todobackend.constants.ApplicationConstants.INVALID_AUTH_TOKEN;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final JwtTokenBlackList tokenBlackList;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            if (this.tokenBlackList.containsToken(jwtToken)) {
                throw new UserException(INVALID_AUTH_TOKEN);
            }

            final String username = this.jwtService.extractUsername(jwtToken);
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            final boolean validateToken = this.jwtService.validateToken(jwtToken, userDetails);

            if (validateToken && SecurityContextHolder.getContext().getAuthentication() == null) {
                final var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                final var authenticationDetails = new WebAuthenticationDetailsSource()
                        .buildDetails(request);
                authenticationToken.setDetails(authenticationDetails);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
