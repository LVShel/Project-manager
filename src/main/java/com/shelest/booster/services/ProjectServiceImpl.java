package com.shelest.booster.services;

import com.shelest.booster.domain.Project;
import com.shelest.booster.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ManagementService managementService;

    private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<Project> showAllProjects() {
        return repository.findAll();
    }

    @Override
    public Project getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public Project getByName(String name) {
        return repository.findByNameEquals(name);
    }

    @Override
    public void removeProject(long id) {
        managementService.removeAllDevelopersFromOneProject(repository.findOne(id));
        repository.delete(id);
        logger.debug("Project with id: {}", id + "fired all developers and vanished!");
    }

    @Override
    public void addProject(Project project) {
        repository.save(project);
        logger.debug("New project started, its ID is: {}", project.getId());
    }

    @Override
    public void updateProject(Project project) {
        repository.save(project);
        logger.debug("Developer with id: {}", project.getId() + "has been updated");
    }

    @Override
    public void updateAllProjects() {
        List<Project> projects = repository.findAll();
        repository.save(projects);
        logger.debug("All projects have been updated");
    }
}
