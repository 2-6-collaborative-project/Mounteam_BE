package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long>, TeamRepositoryCustom {
    List<Team> findAllByOrderByCreateDateDesc();

}
