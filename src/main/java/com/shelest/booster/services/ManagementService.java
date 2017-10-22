package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Manager;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface ManagementService extends UserDetailsService {

    Optional<Manager> findByName(String username);

    Manager findByNameEquals(String name);

    void update(Manager manager);

    void assignTask(Developer developer, Task task);

    void cancelExecuting(Developer developer, Task task);

    void assignAllTasks(List<Project> projectList, List<Task> tasks);

    void cancelAllTasks(List<Project> projectList, List<Task> tasks);

    void assignDeveloperToProject(Developer developer, Project project);

    void removeDeveloperFromProject(Developer developer, Project project);

    void removeAllDevelopersFromOneProject(Project project);
}
