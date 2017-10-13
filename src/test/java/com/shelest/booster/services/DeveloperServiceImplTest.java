package com.shelest.booster.services;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.repositories.DeveloperRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
    public void addDeveloper() throws Exception {
    }

    @Test
    public void updateDeveloper() throws Exception {
    }

    @Test
    public void getByExperienceGreaterThan() throws Exception {
    }

    @Test
    public void getByState() throws Exception {
    }

}