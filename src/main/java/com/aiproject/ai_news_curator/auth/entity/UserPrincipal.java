package com.aiproject.ai_news_curator.auth.entity;

import com.aiproject.ai_news_curator.auth.utils.AuthoritiesUtils;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class UserPrincipal extends User implements UserDetails, OAuth2User {
    private Map<String, Object> attributes;

    public UserPrincipal(User user) {
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setRoles(user.getRoles());
        setProviderType(user.getProviderType());
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal create(User user, Map<String, Object> attribues) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attribues);

        return userPrincipal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthoritiesUtils.getAuthoritiesByEntity(getRoles());
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getUserStatus().equals(UserStatus.MEMBER_ACTIVE);
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
