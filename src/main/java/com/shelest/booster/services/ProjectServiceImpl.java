package com.shelest.booster.services;

import com.shelest.booster.domain.Project;
import com.shelest.booster.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Override
    public Iterable<Project> showAllProjects() {
        return repository.findAll();
    }

    @Override
    public Project getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public void removeProject(long id) {
        repository.delete(id);
    }

    @Override
    public void addProject(Project project) {
        repository.save(project);
    }

    @Override
    public void updateProject(Project project) {
        repository.save(project);
    }
}
