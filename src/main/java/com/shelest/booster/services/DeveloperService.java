package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.utilities.DeveloperExistsException;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.dto.DeveloperDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface DeveloperService extends UserDetailsService {

    List<Developer> showAllDevelopers();

    Developer getById(long id);

    Optional<Developer> getByName(String name);

    void removeDeveloper(long id);

    void addDeveloper(Developer developer);

    void updateDeveloper(Developer developer);

    void updateAllDevelopers();

    List<Developer> getByExperienceGreaterThan(double experience);

    List<Developer> getByNumberOfTasks(int numberOfTasks);

    List<Developer> getByState(State state);

    Page<Developer> getByState(Integer page, Integer size, String order, State state);

    Page<Developer> findAllPageable(Integer page, Integer size, String order);

    Developer registerNewDeveloperAccount(DeveloperDTO accountDto) throws DeveloperExistsException;
}
