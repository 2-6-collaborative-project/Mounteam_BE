package com.example.mountain.domain.team.entity;

import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Transient;

@Transient
@AllArgsConstructor
@Getter
public enum AgeRange {
    TEENS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES("60대"),
    ANY("연령무관");

    @JsonIgnore
    private final String value;

    public static AgeRange fromValue(String value) {
        for (AgeRange ageRange : values()) {
            if (ageRange.value.equalsIgnoreCase(value)) {
                return ageRange;
            }
        }
        throw new CustomException(ErrorCode.NOT_FOUND_AGE);
    }
}
