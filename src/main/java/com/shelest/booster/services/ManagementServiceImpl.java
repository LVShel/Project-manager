package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.SmartTaskDistributor;
import com.shelest.booster.utilities.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManagementServiceImpl implements ManagementService {

    private static Logger logger = LoggerFactory.getLogger(ManagementServiceImpl.class);

    @Override
    public void assignTask(Developer developer, Task task) {
        developer.execute(task);
        logger.debug("Developer ID: {}", developer.getId() + "received task ID: {}", task.getId());
    }

    @Override
    public void cancelExecuting(Developer developer, Task task) {
        developer.stopExecuting(task);
        logger.debug("Developer ID: {}", developer.getId() + "is ordered to cancel task ID: {}", task.getId());
    }

    @Override
    public void assignAllTasks(List<Project> projectList, List<Task> tasks) {
        for(Project project : projectList){
            SmartTaskDistributor taskDistributor = new SmartTaskDistributor(project, tasks);
            taskDistributor.autoAssignAllTasks();
            logger.debug("All not assigned tasks are forced to assign automatically");
        }
    }

    @Override
    public void cancelAllTasks(List<Project> projectList, List<Task> tasks) {
        for(Project project : projectList) {
            SmartTaskDistributor taskDistributor = new SmartTaskDistributor(project, tasks);
            taskDistributor.autoCancelAllTasks();
            logger.debug("All tasks on all projects is forced to cancel automatically");
        }
    }

    @Override
    public void assignDeveloperToProject(Developer developer, Project project) {
        project.addDeveloper(developer);
        logger.debug("Developer ID: {}", developer.getId() + "is ordered to join project ID: {}", project.getId());
    }

    @Override
    public void removeDeveloperFromProject(Developer developer, Project project) {
        project.kickDeveloperToBench(developer);
        logger.debug("Developer ID: {}", developer.getId() + "is ordered to leave project ID: {}", project.getId());
    }

    @Override
    public void removeAllDevelopersFromOneProject(Project project) {
        project.kickAllFromProject();
        logger.debug("Project, ID: {}", project.getId() + "is ordered to kick all developers");
    }

    @Override
    public void assignAllDevelopersToProjects(List<Developer> developers, List<Project> projects) {

    }

    @Override
    public void removeAllDevelopersFromProjects(List<Developer> developers, List<Project> projects) {

    }
}
