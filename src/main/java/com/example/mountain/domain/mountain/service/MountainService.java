package com.example.mountain.domain.mountain.service;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;

    public Mountain findByName(String mountainName) {
        return mountainRepository.findByName(mountainName)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
    }
}
