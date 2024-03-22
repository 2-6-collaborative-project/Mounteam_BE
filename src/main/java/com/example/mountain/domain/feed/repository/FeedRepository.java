package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findAllByOrderByCreateDateDesc();

}
