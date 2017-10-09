package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.utilities.State;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {

    List<Developer> findByExperienceGreaterThan(double experience);

    List<Developer> findByState(State state);
}
