package com.shelest.booster.repositories;

import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);
}
