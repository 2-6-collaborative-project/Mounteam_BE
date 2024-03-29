package com.example.mountain.domain.team.entity;

import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Transient;

@Transient
@Getter
@AllArgsConstructor
public enum Gender {
    WOMAN("여성만"),
    MAN("남성만"),
    ANY("성별무관");

    @JsonIgnore
    private final String value;

    public static Gender fromValue(String value) {
        for (Gender gender : values()) {
            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new CustomException(ErrorCode.NOT_FOUND_GENDER);
    }
}
