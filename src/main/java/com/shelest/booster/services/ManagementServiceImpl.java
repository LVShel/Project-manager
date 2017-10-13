package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManagementServiceImpl implements ManagementService {

    @Override
    public void assignTask(Developer developer, Task task) {
        developer.execute(task);
        developer.getAssignedTasks().add(task);
        task.setStatus(Status.ASSIGNED);
        task.setExecutorID(developer.getId());
    }

    @Override
    public void cancelExecuting(Developer developer, Task task) {
        developer.stopExecuting(task);
        developer.getAssignedTasks().remove(task);
        task.setStatus(Status.NOT_ASSIGNED);
        task.setExecutorID(0);
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
        developer.setState(State.ON_PROJECT);
        developer.setNameOfCurrentProject(project.getName());
    }

    @Override
    public void removeDeveloperFromProject(Developer developer, Project project) {
        developer.setState(State.ON_BENCH);
        developer.setNameOfCurrentProject("Dismissed from project " + project.getName());
        project.dismiss(developer);
    }

    @Override
    public void removeAllDevelopersFromOneProject(Project project) {

        for (Developer developer : project.getDevelopersOnProject()){
            developer.setState(State.ON_BENCH);
            developer.setNameOfCurrentProject("Dismissed from project " + project.getName());
        }
        project.dismissAll();
    }

    @Override
    public void assignAllDevelopersToProjects(List<Developer> developers, List<Project> projects) {

    }

    @Override
    public void removeAllDevelopersFromProjects(List<Developer> developers, List<Project> projects) {

    }
}
