package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.TaskRepository;
import com.shelest.booster.utilities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public List<Task> showAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> getByStatus(Status status) {
        return repository.findByStatus(status);
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

    @Override
    public void updateAllTasks() {
        List<Task> tasks = repository.findAll();
        repository.save(tasks);
    }

    @Override
    public Page<Task> showAllTasks(int page, int size, String order) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        return repository.findAll(pageable);
    }
}
