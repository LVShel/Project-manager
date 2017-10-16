package com.shelest.booster.repositories;

import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    Page<Task> findByStatus(Pageable pageable, Status status);
}
