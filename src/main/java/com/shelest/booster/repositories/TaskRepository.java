package com.shelest.booster.repositories;

import com.shelest.booster.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
