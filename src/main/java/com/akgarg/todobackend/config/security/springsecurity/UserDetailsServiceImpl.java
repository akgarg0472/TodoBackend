package com.akgarg.todobackend.config.security.springsecurity;

import com.akgarg.todobackend.cache.TodoUserCache;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.logger.TodoLogger;
import com.akgarg.todobackend.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final TodoUserCache cache;
    private final TodoLogger logger;

    public UserDetailsServiceImpl(UserRepository userRepository, @Lazy TodoUserCache cache, TodoLogger logger) {
        this.userRepository = userRepository;
        this.cache = cache;
        this.logger = logger;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug(getClass(), "loadUserByUsername() called for {}", username);

        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("No user exists with null/empty username");
        }

        Optional<Object> cachedUser = cache.getValue(username);

        if (cachedUser.isPresent()) {
            logger.debug(getClass(), "loadUserByUsername() returning user from cache: {}", cachedUser.get());

            return new UserDetailsImpl((TodoUser) cachedUser.get());
        }

        Optional<TodoUser> databaseUser = userRepository.findByEmail(username);

        if (databaseUser.isEmpty()) {
            logger.error(getClass(), "loadUserByUsername() -> No user found with email: {}", username);

            throw new UsernameNotFoundException("loadUserByUsername() -> No user found with email " + username);
        }

        logger.debug(getClass(), "loadUserByUsername() -> Returning user from db: {}", databaseUser.get());

        if (Boolean.TRUE.equals(databaseUser.get().getIsAccountNonLocked())) {
            this.cache.insertKeyValue(username, databaseUser.get());
        }

        return new UserDetailsImpl(databaseUser.get());
    }

}
