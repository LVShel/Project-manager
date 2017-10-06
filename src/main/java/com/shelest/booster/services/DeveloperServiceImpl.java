package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository repository;

    @Autowired
    TaskService taskService;

    @Override
    public Iterable<Developer> showAllDevelopers() {
        return repository.findAll();
    }

    @Override
    public Developer getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public void removeDeveloper(long id) {
        repository.delete(id);
    }

    @Override
    public void addDeveloper(Developer developer) {
        repository.save(developer);
    }

    @Override
    public void updateDeveloper(Developer developer) {
        repository.save(developer);
    }



//    @Override
//    public void assignTaskDirectly(Developer developer, Task task){
//        task.execute(developer);
//        developer.getAssignedTasks().add(task);
//    }
//
//    public void cancelExecuting(Developer developer, Task task){
//        task.cancelTask(developer);
//        developer.getAssignedTasks().remove(task);
//    }

    @Override
    public Iterable<Developer> getByExperienceGreaterThan(double experience) {
        return repository.findByExperienceGreaterThan(experience);
    }

//    @Override
//    public void executeAllTasks() {
//
//    }
}
