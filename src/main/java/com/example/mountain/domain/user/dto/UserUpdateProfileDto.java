package com.example.mountain.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateProfileDto {
    private String nickname;
    private String introduction;
    private String ageRange;
    private String areaInterest;
}
