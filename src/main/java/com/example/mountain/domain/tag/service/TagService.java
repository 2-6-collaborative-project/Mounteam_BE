package com.example.mountain.domain.tag.service;

import com.example.mountain.domain.tag.entity.Tag;
import com.example.mountain.domain.tag.repostiory.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    @Transactional
    public List<Tag> saveTag(List<String> names) {
        return names.stream()
                .map(this::findOrElseSave)
                .toList();
    }

    private Tag findOrElseSave(String name) {
        return tagRepository
                .findByName(name)
                .orElseGet(() -> tagRepository.save(Tag.builder()
                        .name(name)
                        .build()));
    }
}
