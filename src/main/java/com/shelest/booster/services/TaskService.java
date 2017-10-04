package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import org.springframework.stereotype.Service;

/**
 * Created by Home on 27.09.2017.
 */
@Service
public interface TaskService {
    Iterable<Task> showAllTasks();

    Task getById(long id);

    void removeTask(long id);

    void addTask(Task task);

    void updateTask(Task task);
}
