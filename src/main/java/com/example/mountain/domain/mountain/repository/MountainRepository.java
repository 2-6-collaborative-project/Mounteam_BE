package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain,Long>, MountainRepositoryCustom {
    @Query("SELECT m FROM Mountain m WHERE m.name = :name")
    Optional<Mountain> findByName(@Param("name") String name);

    @Query("SELECT m.name FROM Mountain m")
    List<String> findAllNames();

    Optional<Mountain> findById (Long mountainId);

    String findByName(Long mountainId);
}
