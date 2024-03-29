package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain,Long> {
    @Query("SELECT m FROM Mountain m WHERE m.name = :name")
    Optional<Mountain> findByName(@Param("name") String name);
}
