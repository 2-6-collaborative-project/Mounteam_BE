package com.example.mountain.domain.mountain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MountainScrollResponse {
    private Long id;
    private String name;
    private String location;
    private String high;
    private int teamCount;
}
