package com.shelest.booster.repositories;

import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.enums.EstimationStatus;
import com.shelest.booster.utilities.enums.ExecutionStatus;
import com.shelest.booster.utilities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByStatusIsNot(Pageable pageable, Status status);

    List<Task> findAllByStatusIsNot(Status status);

    List<Task> findByStatus(Status status);

    List<Task> findByEstimationStatus(EstimationStatus estimationStatus);

    List<Task> findByExecutionStatus(ExecutionStatus executionStatus);

    Page<Task> findByStatus(Pageable pageable, Status status);

    Page<Task> findByExecutionStatusAndStatus(Pageable pageable, ExecutionStatus executionStatus, Status status);

    List<Task> findByExecutionStatusAndStatus(ExecutionStatus executionStatus, Status status);
}
