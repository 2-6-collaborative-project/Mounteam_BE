package com.example.mountain.domain.team.repository;
import com.example.mountain.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team,Long>, TeamRepositoryCustom {

}
