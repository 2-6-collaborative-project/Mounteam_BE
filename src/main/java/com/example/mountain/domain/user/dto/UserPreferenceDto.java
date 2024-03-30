package com.example.mountain.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPreferenceDto {
    String gender;
    String ageRange;
    String areaInterest;
    String locationAgree;
    String privacyAgree;
}
