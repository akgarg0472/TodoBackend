package com.akgarg.todobackend.config.security.springsecurity;

import com.akgarg.todobackend.model.entity.TodoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
public class UserDetailsImpl implements UserDetails {

    private final TodoUser user;

    public UserDetailsImpl(TodoUser todoUser) {
        this.user = todoUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

    public TodoUser getUser() {
        return this.user;
    }

}
