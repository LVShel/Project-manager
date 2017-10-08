package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ManagementService {

    void assignTask(Developer developer, Task task);

    void cancelExecuting(Developer developer, Task task);

    void assignAllTasks(List<Project> projectList, List<Task> tasks);

    void cancelAllTasks(List<Project> projectList, List<Task> tasks);

    void assignDeveloperToProject(Developer developer, Project project);

    void removeDeveloperFromProject(Developer developer, Project project);

    void assignAllDevelopersToProjects(List<Developer> developers, List<Project> projects);

    void removeAllDevelopersFromProjects(List<Developer> developers, List<Project> projects);
}
