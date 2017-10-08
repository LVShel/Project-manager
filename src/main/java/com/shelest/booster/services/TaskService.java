package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import org.springframework.stereotype.Service;


public interface TaskService {
    Iterable<Task> showAllTasks();

    Task getById(long id);

    void removeTask(long id);

    void addTask(Task task);

    void updateTask(Task task);
}
