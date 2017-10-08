package com.shelest.booster.repositories;

import com.shelest.booster.domain.Project;
import org.springframework.data.repository.CrudRepository;


public interface ProjectRepository extends CrudRepository<Project, Long> {
}
