package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.Status;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {
    List<Task> showAllTasks();

    List<Task> getByStatus(Status status);

    Task getById(long id);

    void removeTask(long id);

    void addTask(Task task);

    void updateTask(Task task);

    void updateAllTasks();

    Page<Task> showAllTasks(int page, int size, String order);
}
