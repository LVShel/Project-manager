package com.shelest.booster.repositories;

import com.shelest.booster.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByNameEquals(String name);
}
