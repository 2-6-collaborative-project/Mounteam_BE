package com.example.mountain.domain.team.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum AgeRange {
    teenager,
    twenties,
    thirties,
    fourties,
    fifties,
    sixties;

    public static AgeRange fromString (String value) {
        for (AgeRange ageRange : AgeRange.values()) {
            if (ageRange.name().equalsIgnoreCase(value)) {
                return ageRange;
            }
        }
        throw new IllegalArgumentException("No such age range: " + value);
    }
}