package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ManagementService managementService;

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
        managementService.removeAllDevelopersFromOneProject(repository.findOne(id));
        repository.delete(id);//todo remove all developers before
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
