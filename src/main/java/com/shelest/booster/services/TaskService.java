package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.enums.EstimationStatus;
import com.shelest.booster.utilities.enums.ExecutionStatus;
import com.shelest.booster.utilities.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;


public interface TaskService {
    List<Task> showAllTasks();

    List<Task> getByStatus(Status status);

    List<Task> getByEstimationStatus(EstimationStatus estimationStatus);

    List<Task> getByExecutionStatus(ExecutionStatus executionStatus);

    List<Task> getAllByStatusIsNot(Status status);

    Page<Task> showAllTaskByStatusIsNot(int page, int size, String order, Status status);

    Task getById(long id);

    void removeTask(long id);

    void addTask(Task task);

    void updateTask(Task task);

    void updateAllTasks();

    Page<Task> showAllTasks(int page, int size, String order);

    Page<Task> showAllTasksByStatus(int page, int size, String order, Status status);

    Page<Task> showAllTasksByExecutionStatusAndStatus(int page, int size, String order, ExecutionStatus executionStatus, Status status);


    List<Task> showAllTasksByExecutionStatusAndStatus(ExecutionStatus executionStatus, Status status);
}
