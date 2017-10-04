package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import org.springframework.stereotype.Service;

/**
 * Created by Home on 26.09.2017.
 */
@Service
public interface DeveloperService {

    Iterable<Developer> showAllDevelopers();

    Developer getById(long id);

    void removeDeveloper(long id);

    void addDeveloper(Developer developer);

    void updateDeveloper(Developer developer);

    void assignTaskDirectly(Developer developer, Task task);

    void cancelExecuting(Developer developer, Task task);

    void executeAllTasks();



}
