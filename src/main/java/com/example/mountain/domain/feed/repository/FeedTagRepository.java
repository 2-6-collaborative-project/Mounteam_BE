package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.entity.FeedTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedTagRepository extends JpaRepository<FeedTagMap, Long> {
}
