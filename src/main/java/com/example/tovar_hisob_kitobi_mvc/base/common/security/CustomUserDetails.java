package com.example.tovar_hisob_kitobi_mvc.base.common.security;

import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(User user)implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getLavozim().name()));
    }

    @Override
    public String getPassword() {
        return user.getParol();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }
}
