package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;

import java.util.List;

/**
 * Created by Home on 06.10.2017.
 */
public interface ManagementService {

    void assignTask(Developer developer, Task task);

    void cancelExecuting(Developer developer, Task task);

    void assignAllTasks(List<Project> projectList);

    void cancelAllTasks(List<Project> projectList);
}
