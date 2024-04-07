package com.example.mountain.domain.Tag.Service;

import com.example.mountain.domain.Tag.entity.Tag;
import com.example.mountain.domain.Tag.repostiory.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
