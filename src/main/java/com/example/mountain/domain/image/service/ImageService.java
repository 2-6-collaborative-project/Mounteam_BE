package com.example.mountain.domain.image.service;

import com.example.mountain.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void deleteByReviewId (Long reviewId) {
        imageRepository.deleteByReviewId(reviewId);
    }
}
