package com.example.mountain.domain.team.entity;

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
}
