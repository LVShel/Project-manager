package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManagementServiceImpl implements ManagementService {

    @Autowired
    ProjectService projectService;

    @Autowired
    DeveloperService developerService;

    @Autowired
    TaskService taskService;

    @Override
    public void assignTask(Developer developer, Task task) {
        developer.execute(task);
    }

    @Override
    public void cancelExecuting(Developer developer, Task task) {
        developer.stopExecuting(task);
    }

    @Override
    public void assignAllTasks(List<Project> projectList, List<Task> tasks) {

    }

    @Override
    public void cancelAllTasks(List<Project> projectList, List<Task> tasks) {

    }

    @Override
    public void assignDeveloperToProject(Developer developer, Project project) {
        project.getDevelopersOnProject().add(developer);
    }

    @Override
    public void removeDeveloperFromProject(Developer developer, Project project) {
        project.getDevelopersOnProject().remove(developer);
    }

    @Override
    public void assignAllDevelopersToProjects(List<Developer> developers, List<Project> projects) {

    }

    @Override
    public void removeAllDevelopersFromProjects(List<Developer> developers, List<Project> projects) {

    }
}
