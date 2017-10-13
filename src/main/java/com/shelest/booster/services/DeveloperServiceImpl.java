package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.DeveloperRepository;
import com.shelest.booster.utilities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository repository;

    @Override
    public List<Developer> showAllDevelopers() {
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
    public List<Developer> getByExperienceGreaterThan(double experience) {
        return repository.findByExperienceGreaterThan(experience);
    }

    @Override
    public List<Developer> getByState(State state) {
        return repository.findByState(state);
    }

    @Override
    public Page<Developer> findAllPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
