package com.akgarg.todobackend.config.security.springsecurity;

import com.akgarg.todobackend.config.security.jwt.JwtAuthenticationEntryPoint;
import com.akgarg.todobackend.config.security.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(
            final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder
    ) {
        final var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/account/**", "/api/v1/password/**", "/health", "/api/v1/cache/reloadCache", "/").permitAll()
                .antMatchers("/api/v1/admins/**").hasRole("ADMIN")
                .antMatchers("/api/v1/cache/**").hasRole("ADMIN")
                .antMatchers("/api/v1/users/**").hasRole("USER")
                .antMatchers("/api/v1/**")
                .authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .accessDeniedHandler(this.accessDeniedExceptionHandler);

        return http.build();
    }

}
