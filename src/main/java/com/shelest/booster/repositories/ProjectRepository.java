package com.shelest.booster.repositories;

import com.shelest.booster.domain.Project;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Home on 06.10.2017.
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
