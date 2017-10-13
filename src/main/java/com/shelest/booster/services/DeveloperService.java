package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DeveloperService {

    List<Developer> showAllDevelopers();

    Developer getById(long id);

    void removeDeveloper(long id);

    void addDeveloper(Developer developer);

    void updateDeveloper(Developer developer);

    List<Developer> getByExperienceGreaterThan(double experience);

    List<Developer> getByState(State state);


    Page<Developer> findAllPageable(Pageable pageable);
}
