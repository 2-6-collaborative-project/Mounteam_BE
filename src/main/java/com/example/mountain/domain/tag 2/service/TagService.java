package com.example.mountain.domain.tag.service;

import com.example.mountain.domain.tag.repostiory.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

}
