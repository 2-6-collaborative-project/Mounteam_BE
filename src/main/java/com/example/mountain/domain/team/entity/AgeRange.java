package com.example.mountain.domain.team.entity;

import org.springframework.security.core.Transient;

@Transient
public enum AgeRange {
    TEENS,
    TWENTIES,
    THIRTIES,
    FORTIES,
    FIFTIES,
    SIXTIES,
    ANY //연령상관없음
}
