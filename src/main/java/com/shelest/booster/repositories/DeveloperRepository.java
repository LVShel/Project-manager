package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Home on 25.09.2017.
 */
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
    Collection<Developer> findByExperienceGreaterThan(double experience);
}
