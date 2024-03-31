package com.example.mountain.domain.image.service;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void deleteByFeedId (Long feedId) {
        imageRepository.deleteByFeedId(feedId);
    }
}
