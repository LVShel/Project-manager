package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.SmartHelper;
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
    }

    @Override
    public void cancelExecuting(Developer developer, Task task) {
        developer.stopExecuting(task);
    }

    @Override
    public void assignAllTasks(List<Project> projectList, List<Task> tasks) {
        for(Project project : projectList){
            SmartHelper helper = new SmartHelper(project, tasks);
            helper.autoAssignAllTasks();
        }
    }

    @Override
    public void cancelAllTasks(List<Project> projectList, List<Task> tasks) {
        for(Project project : projectList) {
            SmartHelper helper = new SmartHelper(project, tasks);
            helper.autoCancelAllTasks();
        }
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
