package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.repositories.DeveloperRepository;
import com.shelest.booster.utilities.dto.DeveloperDTO;
import com.shelest.booster.utilities.enums.State;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
public class DeveloperServiceImplTest {

    @TestConfiguration
    static class DeveloperServiceImplTestContextConfiguration {

        @Bean
        public DeveloperService developerService() {
            return new DeveloperServiceImpl();
        }
    }

    @MockBean
    private DeveloperRepository repository;

    @MockBean
    DeveloperDTO developerDTO;

    @Autowired
    private DeveloperService developerService;

    @Test
    public void showAllDevelopers() throws Exception {
        List<Developer> developerList = new ArrayList<>();
        developerList.add(new Developer("Rower"));

        doReturn(developerList).when(repository).findAll();

        List<Developer> found = developerService.showAllDevelopers();

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isExactlyInstanceOf(Developer.class);
        verify(repository).findAll();
    }

    @Test
    public void getById() throws Exception {
        long id = 1;
        Developer developer = new Developer();

        doReturn(developer).when(repository).findOne(id);

        Developer found = developerService.getById(id);

        assertThat(found).isNotNull();
        verify(repository).findOne(id);
    }

    @Test
    public void removeDeveloper() throws Exception {
        long id = 1;
        Developer developer = new Developer();
        developer.setId(id);

        doReturn(developer).when(repository).findOne(id);

        developerService.removeDeveloper(id);

        verify(repository).delete(id);
    }

    @Test
    public void getByNameEquals() throws Exception {
        String name = "Alex";
        Developer developer = developerService.getByNameEquals(name);
        verify(repository).findByNameEquals(name);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getByName() throws Exception {
        String name = "Alex";
        Optional<Developer> developer = developerService.getByName(name);
        verify(repository).findByName(name);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateDeveloper() throws Exception {
        long id = 1;
        Developer developer = new Developer("Alex");
        when(repository.findOne(id)).thenReturn(developer);
        developer.setName("Rower");
        developerService.updateDeveloper(developer);

        assertEquals(developer.getName(), "Rower");
    }

    @Test
    public void updateAllDevelopers() throws Exception {
        Developer developer = new Developer("Alex");
        Developer developer1 = new Developer("Leo");
        List<Developer> all = new ArrayList<>();
        all.add(developer);
        all.add(developer1);

        when(repository.findAll()).thenReturn(all);
        developer.setName("Zed");
        developer1.setName("Zaz");

        developerService.updateAllDevelopers();
        verify(repository).save(all);
        assertEquals(developer.getName(), "Zed");
        assertEquals(developer1.getName(), "Zaz");


    }

    @Test
    public void getByExperienceGreaterThan() throws Exception {
        int experience = 30;

        List<Developer> found = developerService.getByExperienceGreaterThan(experience);
        verify(repository).findByExperienceGreaterThan(experience);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getByNumberOfTasks() throws Exception {
        int numberOfTasks = 30;

        List<Developer> found = developerService.getByNumberOfTasks(numberOfTasks);
        verify(repository).findByNumberOfTasksEquals(numberOfTasks);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getByState() throws Exception {
        State state = State.ON_BENCH;

        List<Developer> found = developerService.getByState(state);
        verify(repository).findByState(state);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getByState1() throws Exception {
        List<Developer> expected = new ArrayList<>();
        Page expectedPage = new PageImpl(expected);
        when(repository.findByState(any(Pageable.class),eq(State.ON_BENCH))).thenReturn(expectedPage);

        Page<Developer> actual = developerService.getByState(1, 8, "Id", State.ON_BENCH);
        verify(repository).findByState(any(Pageable.class), eq(State.ON_BENCH));
        assertNotNull(actual);
    }

    @Test
    public void findAllPageable() throws Exception {
        List<Developer> all = new ArrayList<>();
        Page expectedPage = new PageImpl(all);
        when(repository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<Developer> actual = developerService.findAllPageable(1, 8, "Id", 1);
        verify(repository).findAll(any(Pageable.class));
        assertNotNull(actual);
    }

    @Test
    public void findAllPageable1() throws Exception {
        List<Developer> expected = new ArrayList<>();
        Page expectedPage = new PageImpl(expected);
        when(repository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<Developer> actual = developerService.findAllPageable(1, 8, "Id", 2);
        verify(repository).findAll(any(Pageable.class));
        assertNotNull(actual);
    }

    @Test
    public void registerNewDeveloperAccount() throws Exception {

    }

    @Test
    public void loadUserByUsername() throws Exception {

    }


}