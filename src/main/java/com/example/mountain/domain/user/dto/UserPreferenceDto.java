package com.example.mountain.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPreferenceDto {
    private String gender;
    private String ageRange;
    private String areaInterest;
    private String locationAgree;
    private String privacyAgree;
}
