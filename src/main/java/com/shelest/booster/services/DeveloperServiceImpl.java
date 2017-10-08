package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.DeveloperRepository;
import com.shelest.booster.utilities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    public Iterable<Developer> getByExperienceGreaterThan(double experience) {
        return repository.findByExperienceGreaterThan(experience);
    }

    @Override
    public Iterable<Developer> getByState(State state) {
        return repository.findByState(state);
    }
}
