package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;

import java.util.List;

/**
 * Created by Home on 06.10.2017.
 */
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
    public void assignAllTasks(List<Project> projectList) {

    }

    @Override
    public void cancelAllTasks(List<Project> projectList) {

    }
}
