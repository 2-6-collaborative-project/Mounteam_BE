package com.example.mountain.domain.tag.Service;

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

}
