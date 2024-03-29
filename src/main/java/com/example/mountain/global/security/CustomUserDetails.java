package com.example.mountain.global.security;

import com.example.mountain.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;
    private Long userId;
    private String nickname;
    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(User user){
        this.user = user;
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }

    // OAuth 로그인
    public CustomUserDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.attributes = attributes;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(n -> {
            authorities.add(() -> n);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getUserId());
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
        // ex) 1년동안 로그인 안하면 휴먼계정
        return true;
    }

    // OAuth2User //
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
