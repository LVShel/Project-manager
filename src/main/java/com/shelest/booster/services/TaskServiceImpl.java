package com.shelest.booster.services;

import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.TaskRepository;
import com.shelest.booster.utilities.enums.EstimationStatus;
import com.shelest.booster.utilities.enums.ExecutionStatus;
import com.shelest.booster.utilities.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


    @Override
    public List<Task> showAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> getByStatus(Status status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Task> getByEstimationStatus(EstimationStatus estimationStatus) {
        return repository.findByEstimationStatus(estimationStatus);
    }

    @Override
    public List<Task> getByExecutionStatus(ExecutionStatus executionStatus) {
        return repository.findByExecutionStatus(executionStatus);
    }

    @Override
    public List<Task> getAllByStatusIsNot(Status status) {
        return repository.findAllByStatusIsNot(status);
    }

    @Override
    public Page<Task> showAllTaskByStatusIsNot(int page, int size, String order, Status status) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Task> taskPage = repository.findAllByStatusIsNot(pageable, status);
        logger.debug("Method showAllTasksByStatus(Pageable, Status) returned tasks: {}", taskPage.getTotalElements());
        return taskPage;
    }

    @Override
    public Task getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public void removeTask(long id) {
        repository.delete(id);
        logger.debug("Task with id: {}", id + "stopped executing and has been deleted");
    }

    @Override
    public void addTask(Task task) {
        repository.save(task);
        logger.debug("New task created, its ID is: {}", task.getId());
    }

    @Override
    public void updateTask(Task task) {
        repository.save(task);
        logger.debug("Task with id: {}", task.getId() + "has been updated");
    }

    @Override
    public void updateAllTasks() {
        List<Task> tasks = repository.findAll();
        repository.save(tasks);
        logger.debug("All tasks have been updated");
    }

    @Override
    public Page<Task> showAllTasks(int page, int size, String order) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Task> taskPage = repository.findAll(pageable);
        logger.debug("Method showAllTasks(Pageable) returned tasks: {}", taskPage.getTotalElements());
        return taskPage;
    }

    @Override
    public Page<Task> showAllTasksByStatus(int page, int size, String order, Status status) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Task> taskPage = repository.findByStatus(pageable, status);
        logger.debug("Method showAllTasksByStatus(Pageable, Status) returned tasks: {}", taskPage.getTotalElements());
        return taskPage;
    }

    @Override
    public Page<Task> showAllTasksByExecutionStatusAndStatus(int page, int size, String order, ExecutionStatus executionStatus, Status status) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Task> taskPage = repository.findByExecutionStatusAndStatus(pageable, executionStatus, status);
        logger.debug("Method showAllTasksByStatus(Pageable, Status) returned tasks: {}", taskPage.getTotalElements());
        return taskPage;
    }

    @Override
    public List<Task> showAllTasksByExecutionStatusAndStatus(ExecutionStatus executionStatus, Status status) {
        return repository.findByExecutionStatusAndStatus(executionStatus, status);
    }
}
