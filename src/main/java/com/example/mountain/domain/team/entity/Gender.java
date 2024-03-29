package com.example.mountain.domain.team.entity;

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
}
