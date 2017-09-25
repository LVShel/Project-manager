package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Home on 25.09.2017.
 */
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
}
