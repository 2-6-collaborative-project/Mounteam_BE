package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListScrollResponse;
import com.example.mountain.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface TeamRepository extends JpaRepository<Team,Long>, TeamRepositoryCustom {


}
