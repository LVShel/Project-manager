package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.Pager;
import com.shelest.booster.utilities.enums.Rank;
import com.shelest.booster.utilities.enums.State;
import com.shelest.booster.utilities.enums.Status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProjectControllerTest {

    @Mock
    private DeveloperService developerService;

    @Mock
    private TaskService taskService;

    @Mock
    private ManagementService managementService;

    @Mock
    private ProjectService projectService;

    @Mock
    private Pager pager;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    private static final long DEVELOPER_ID = 1;
    private static final long TASK_ID = 1;
    private static final long PROJECT_ID = 1;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void listProjects() throws Exception {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());

        when(projectService.showAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects/"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/projectList"))
                .andExpect(model().attribute("projects", hasSize(2)));
    }

    @Test
    public void deleteProject() throws Exception {
        mockMvc.perform(get("/projects/1/deleteProject"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));

        verify(projectService).removeProject(PROJECT_ID);
    }

    @Test
    public void newProject() throws Exception {
        verifyZeroInteractions(projectService);

        mockMvc.perform(get("/projects/newProject"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/newProject"));
    }

    @Test
    public void create() throws Exception {

        mockMvc.perform(post("/projects/createProject")
                .param("project_name", "ALFA")
                .param("seniorsNeed", "3")
                .param("middlesNeed", "3")
                .param("juniorsNeed", "3")
                .param("maxTasks", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));

        ArgumentCaptor<Project> boundProject = ArgumentCaptor.forClass(Project.class);
        verify(projectService).addProject(boundProject.capture());
        assertEquals("ALFA", boundProject.getValue().getName());
    }

    @Test
    public void update() throws Exception {
        Project project = new Project();
        project.setId(1);
        when(projectService.getById(1)).thenReturn(project);

        mockMvc.perform(post("/projects/updateProject")
                .param("project_id", "1")
                .param("project_name", "ALFA")
                .param("seniorsNeed", "3")
                .param("middlesNeed", "3")
                .param("juniorsNeed", "3")
                .param("maxTasks", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));

        ArgumentCaptor<Project> boundProject = ArgumentCaptor.forClass(Project.class);
        verify(projectService).updateProject(boundProject.capture());
        assertEquals("ALFA", boundProject.getValue().getName());
    }

    @Test
    public void edit() throws Exception {
        when(projectService.getById(PROJECT_ID)).thenReturn(new Project());

        mockMvc.perform(get("/projects/1/editProject"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/editProject"))
                .andExpect(model().attribute("project", instanceOf(Project.class)));
    }

    @Test
    public void listAssignedDevelopers() throws Exception {
        Project project = new Project();
        List<Developer> developers = new ArrayList<>();
        developers.add(new Developer());
        developers.add(new Developer());
        project.setDevelopersOnProject(developers);

        doReturn(project).when(projectService).getById(PROJECT_ID);

        mockMvc.perform(get("/projects/1/showTeam"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/projectTeam"))
                .andExpect(model().attribute("developersOnProject", hasSize(2)));
    }

    @Test
    public void removeDeveloper() throws Exception {
        Project project = new Project();
        project.setId(PROJECT_ID);
        Developer developer = new Developer();
        developer.setState(State.ON_BENCH);
        developer.setId(DEVELOPER_ID);

        doReturn(project).when(projectService).getById(PROJECT_ID);
        doReturn(developer).when(developerService).getById(DEVELOPER_ID);

        mockMvc.perform(get("/projects/1/showTeam/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));

        verify(managementService).removeDeveloperFromProject(developer, project);
        verify(projectService).updateProject(project);
    }

}