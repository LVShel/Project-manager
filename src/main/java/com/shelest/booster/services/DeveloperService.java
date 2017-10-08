package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.State;
import org.springframework.stereotype.Service;

public interface DeveloperService {

    Iterable<Developer> showAllDevelopers();

    Developer getById(long id);

    void removeDeveloper(long id);

    void addDeveloper(Developer developer);

    void updateDeveloper(Developer developer);

    Iterable<Developer> getByExperienceGreaterThan(double experience);

    Iterable<Developer> getByState(State state);



}
