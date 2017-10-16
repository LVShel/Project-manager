package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.SmartTaskDistributor;
import com.shelest.booster.utilities.State;
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
            SmartTaskDistributor taskDistributor = new SmartTaskDistributor(project, tasks);
            taskDistributor.autoAssignAllTasks();
        }
    }

    @Override
    public void cancelAllTasks(List<Project> projectList, List<Task> tasks) {
        for(Project project : projectList) {
            SmartTaskDistributor taskDistributor = new SmartTaskDistributor(project, tasks);
            taskDistributor.autoCancelAllTasks();
        }
    }

    @Override
    public void assignDeveloperToProject(Developer developer, Project project) {
        project.addDeveloper(developer);
    }

    @Override
    public void removeDeveloperFromProject(Developer developer, Project project) {
        project.kickDeveloperToBench(developer);
    }

    @Override
    public void removeAllDevelopersFromOneProject(Project project) {
        project.kickAllFromProject();
    }

    @Override
    public void assignAllDevelopersToProjects(List<Developer> developers, List<Project> projects) {

    }

    @Override
    public void removeAllDevelopersFromProjects(List<Developer> developers, List<Project> projects) {

    }
}
