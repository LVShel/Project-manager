package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.utilities.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    List<Developer> findByExperienceGreaterThan(double experience);

    List<Developer> findByNumberOfTasksEquals(int numberOfTasks);

    List<Developer> findByState(State state);

    Page<Developer> findByState(Pageable pageable, State state);

    Optional<Developer> findByName(String username);

    Developer findByNameEquals(String username);
}
