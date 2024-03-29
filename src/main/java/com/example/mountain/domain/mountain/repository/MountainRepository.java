package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainRepository extends JpaRepository<Mountain,Long> {
    Mountain findByName (String mountainName);
}
