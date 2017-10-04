package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Home on 27.09.2017.
 */
public class TaskServiceIml implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public Iterable<Task> showAllTasks() {
        return repository.findAll();
    }

    @Override
    public Task getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public void removeTask(long id) {
        repository.delete(id);
    }

    @Override
    public void addTask(Task task) {
        repository.save(task);
    }

    @Override
    public void updateTask(Task task) {
        repository.save(task);
    }
}
