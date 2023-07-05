package com.example.binanceAnalyzer.Models.Plain;

import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserPrincipal implements UserDetails {
    public UserPrincipal(User user) {
        this.user = user;
    }
    private User user;
    public static void setEncoder(PasswordEncoder encoder) {
        UserPrincipal.encoder = encoder;
    }

    private static PasswordEncoder encoder;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singletonList(new SimpleGrantedAuthority(user.getUserProperties().getRole()));
    }

    @Override
    public String getPassword() {
      return encoder.encode(user.getPass());
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
