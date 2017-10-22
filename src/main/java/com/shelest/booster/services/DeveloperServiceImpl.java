package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.repositories.DeveloperRepository;
import com.shelest.booster.utilities.CustomUserDetails;
import com.shelest.booster.utilities.DeveloperExistsException;
import com.shelest.booster.utilities.enums.State;
import com.shelest.booster.utilities.dto.DeveloperDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository repository;

    private static Logger logger = LoggerFactory.getLogger(DeveloperServiceImpl.class);

    @Override
    public List<Developer> showAllDevelopers() {
        return repository.findAll();
    }

    @Override
    public Developer getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public Developer getByNameEquals(String username) {
        return repository.findByNameEquals(username);
    }

    @Override
    public Optional<Developer> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void removeDeveloper(long id) {
        repository.findOne(id).stopExecutingAllTasks();
        repository.delete(id);
        logger.debug("Developer with id: {}", id + "stopped executing all tasks and fired");
    }

    @Override
    public void addDeveloper(Developer developer) {
        repository.save(developer);
        logger.debug("New developer adopted, his ID is: {}", developer.getId());
    }

    @Override
    public void updateDeveloper(Developer developer) {
        repository.save(developer);
        logger.debug("Developer with id: {}", developer.getId() + "has been updated");
    }

    @Override
    public void updateAllDevelopers() {
        List<Developer> developers = repository.findAll();
        repository.save(developers);
        logger.debug("All developers have been updated!");
    }

    @Override
    public List<Developer> getByExperienceGreaterThan(double experience) {
        List<Developer> developers = repository.findByExperienceGreaterThan(experience);
        logger.debug("Method getByExperienceGreaterThan(double) returned developers: {}", developers.size());
        return developers;
    }

    @Override
    public List<Developer> getByNumberOfTasks(int numberOfTasks) {
        List<Developer> developers = repository.findByNumberOfTasksEquals(numberOfTasks);
        logger.debug("Method getByNumberOfTasks(int) returned developers: {}", developers.size());
        return developers;
    }

    @Override
    public List<Developer> getByState(State state) {
        List<Developer> developers = repository.findByState(state);
        logger.debug("Method getByState(State) returned developers: {}", developers.size());
        return developers;
    }

    @Override
    public Page<Developer> getByState(Integer page, Integer size, String order, State state) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Developer> developerPage = repository.findByState(pageable, state);
        logger.debug("Method getByState(State, Pageable) returned developers: {}", developerPage.getTotalElements());
        return developerPage;
    }

    @Override
    public Page<Developer> findAllPageable(Integer page, Integer size, String order, Integer direction) {
        if (StringUtils.isEmpty(order)) {
            order = "id";
        }
        Sort.Direction dir;
        if(direction==1){
            dir = Sort.Direction.ASC;
        }
        else {
            dir = Sort.Direction.DESC;
        }
        Sort sort = new Sort(new Sort.Order(dir, order));
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Developer> developerPage = repository.findAll(pageable);
        logger.debug("Method findAllPageable(Pageable) returned developers: {}", developerPage.getTotalElements());
        return developerPage;
    }

    @Transactional
    @Override
    public Developer registerNewDeveloperAccount(DeveloperDTO accountDto) throws DeveloperExistsException {
        if (nameExist(accountDto.getName())) {
            throw new DeveloperExistsException(
                    "There is an account with that userName: "
                            +  accountDto.getName());
        }
        Developer developer = new Developer();
        developer.setName(accountDto.getName());
        developer.setRank(accountDto.getRank());
        developer.setExperience(accountDto.getExperience());
        developer.setQualification(accountDto.getQualification());
        developer.setPassword(accountDto.getPassword());
        repository.save(developer);
        return developer;
    }

    private boolean nameExist(String name) {
        Developer developer = repository.findByNameEquals(name);
        if (developer != null) {
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Developer> optionalUsers = repository.findByName(username);

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUsers
                .map(CustomUserDetails::new).get();
    }
}
