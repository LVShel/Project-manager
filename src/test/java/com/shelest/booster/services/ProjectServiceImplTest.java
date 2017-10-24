package com.shelest.booster.services;

import com.shelest.booster.domain.Project;
import com.shelest.booster.repositories.ManagerRepository;
import com.shelest.booster.repositories.ProjectRepository;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProjectServiceImplTest {
    @TestConfiguration
    static class ProjectsServiceImplTestContextConfiguration {

        @Bean
        public ProjectService projectService() {
            return new ProjectServiceImpl();
        }

        @Bean
        ManagementService managementService() {
            return new ManagementServiceImpl();
        }
    }

    @MockBean
    private ProjectRepository repository;

    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ManagementService managementService;

    @Test
    public void showAllProjects() throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project());

        doReturn(projectList).when(repository).findAll();

        List<Project> found = projectService.showAllProjects();

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isExactlyInstanceOf(Project.class);
        verify(repository).findAll();
    }

    @Test
    public void getById() throws Exception {
        long id = 1;
        Project project = new Project();

        doReturn(project).when(repository).findOne(id);

        Project found = projectService.getById(id);

        assertThat(found).isNotNull();
        verify(repository).findOne(id);
    }

    @Test
    public void getByName() throws Exception {
        String name = "ALFA";
        Project project = projectService.getByName(name);
        verify(repository).findByNameEquals(name);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void removeProject() throws Exception {
        long id = 1;
        Project project = new Project();
        project.setId(id);

        doReturn(project).when(repository).findOne(id);

        projectService.removeProject(id);

        verify(repository).delete(id);
    }

    @Test
    public void addProject() throws Exception {
        Project project = new Project();

        projectService.addProject(project);
        verify(repository).save(project);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateProject() throws Exception {
        long id = 1;
        Project project = new Project();

        when(repository.findOne(id)).thenReturn(project);
        project.setName("GAMMA");
        projectService.updateProject(project);

        assertEquals(project.getName(), "GAMMA");
        verify(repository).save(project);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateAllProjects() throws Exception {
        List<Project> all = new ArrayList<>();
        all.add(new Project());
        all.add(new Project());

        when(repository.findAll()).thenReturn(all);

        projectService.updateAllProjects();
        verify(repository).save(all);
    }

}