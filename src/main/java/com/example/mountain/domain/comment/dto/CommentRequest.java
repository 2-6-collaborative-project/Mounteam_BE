package com.example.mountain.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
    @NotNull
    @Size(max = 1000)
    private String content;
}
