package com.example.mountain.domain.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String account;
    private String password;
    private String nickname;

    @Getter
    public static class LoginUserDto {
        private String account;
        private String password;
    }
}
