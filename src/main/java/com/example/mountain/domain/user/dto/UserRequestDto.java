package com.example.mountain.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String account;
    private String password;
    private String nickname;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginUserDto {
        private String account;
        private String password;
    }
}
