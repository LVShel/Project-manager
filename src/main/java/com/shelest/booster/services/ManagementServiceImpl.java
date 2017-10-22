package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Manager;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.repositories.ManagerRepository;
import com.shelest.booster.utilities.CustomManagerDetails;
import com.shelest.booster.utilities.SmartTaskDistributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    ManagerRepository repository;

    private static Logger logger = LoggerFactory.getLogger(ManagementServiceImpl.class);

    @Override
    public Optional<Manager> findByName(String username) {
        return repository.findByName(username);
    }

    @Override
    public Manager findByNameEquals(String name) {
        return repository.findByNameEquals(name);
    }

    @Override
    public void update(Manager manager) {
        repository.save(manager);
    }

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
        for (Project project : projectList) {
            SmartTaskDistributor taskDistributor = new SmartTaskDistributor(project, tasks);
            taskDistributor.autoAssignAllTasks();
            logger.debug("All not assigned tasks are forced to assign automatically");
        }
    }

    @Override
    public void cancelAllTasks(List<Project> projectList, List<Task> tasks) {
        for (Project project : projectList) {
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Manager> optionalUsers = repository.findByName(username);

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUsers
                .map(CustomManagerDetails::new).get();
    }
}
