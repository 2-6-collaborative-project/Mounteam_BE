package com.example.mountain.domain.team.entity;

import org.springframework.security.core.Transient;

@Transient
public enum Gender {
    WOMAN, MAN, ANY //여성만, 남성만, 성별상관없음
}
