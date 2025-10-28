package com.aiproject.ai_news_curator.auth.utils;

import com.aiproject.ai_news_curator.auth.entity.Authorities;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthoritiesUtils {
    private final Set<String> adminEmailSet;

    public AuthoritiesUtils(@Value("${admin.email}") String adminEmails) {
        this.adminEmailSet = Set.of(Arrays.stream(adminEmails.split(","))
            .map(String::trim)
            .toArray(String[]::new));
    }

    public List<String> createRoles(String email) {
        if (adminEmailSet != null && adminEmailSet.contains(email)) {
            return Stream.of(UserRole.values())
                    .map(UserRole::name)
                    .toList();
        }

        return List.of(UserRole.USER.name());
    }

    public List<Authorities> createAuthorities(User user) {
        return createRoles(user.getEmail()).stream()
                .map(role -> new Authorities(user, role))
                .toList();
    }

    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public static List<GrantedAuthority> getAuthoritiesByEntity(List<Authorities> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toList());
    }
}
