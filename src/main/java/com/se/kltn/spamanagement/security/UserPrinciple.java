package com.se.kltn.spamanagement.security;

import com.se.kltn.spamanagement.model.Account;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class UserPrinciple implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple createUser(Account account) {
        Set<SimpleGrantedAuthority> grantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()));
        return UserPrinciple.builder()
                .id(account.getId())
                .username(builder().username)
                .password(builder().password)
                .authorities(grantedAuthorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return false;
    }
}
