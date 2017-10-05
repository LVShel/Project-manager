package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.Rank;
import com.shelest.booster.utilities.Status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class DeveloperControllerTest {

    @Mock
    private DeveloperService developerService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private DeveloperController developerController;

    private MockMvc mockMvc;

    private static final long DEVELOPER_ID = 1;
    private static final long TASK_ID = 1;
    private static final String NAME = "NoName";
    private static final Rank RANK = Rank.MIDDLE;
    private static final double EXPERIENCE = 2.;
    private static final int QUALIFICATION = 40;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
    }

    @Test
    public void listDevelopers() throws Exception {
        List<Developer> developers = new ArrayList<>();
        developers.add(new Developer());
        developers.add(new Developer());

        when(developerService.showAllDevelopers()).thenReturn(developers);

        mockMvc.perform(get("/developers"))
                .andExpect(status().isOk())
                .andExpect(view().name("developers/list"))
                .andExpect(model().attribute("developers", hasSize(2)));
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(get("/developers/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/developers"));

        verify(developerService, times(1)).removeDeveloper(DEVELOPER_ID);
    }

    @Test
    public void newDeveloper() throws Exception {

        verifyZeroInteractions(developerService);

        mockMvc.perform(get("/developers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("developers/new"));
    }

    @Test
    public void create() throws Exception {

        mockMvc.perform(post("/developers/create")
                .param("name", NAME)
                .param("rank", String.valueOf(RANK))
                .param("experience", String.valueOf(EXPERIENCE))
                .param("qualification", String.valueOf(QUALIFICATION)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/developers"));

        ArgumentCaptor<Developer> boundDeveloper = ArgumentCaptor.forClass(Developer.class);
        verify(developerService).addDeveloper(boundDeveloper.capture());
        assertEquals(NAME, boundDeveloper.getValue().getName());
        assertEquals(RANK, boundDeveloper.getValue().getRank());
        assertEquals(EXPERIENCE, boundDeveloper.getValue().getExperience(), DELTA);
        assertEquals(QUALIFICATION, boundDeveloper.getValue().getQualification());
    }

    @Test
    public void update() throws Exception {

        Developer oldDeveloper = new Developer();
        oldDeveloper.setId(DEVELOPER_ID);
        oldDeveloper.setName("zaz");
        oldDeveloper.setRank(Rank.JUNIOR);
        oldDeveloper.setExperience(1);
        oldDeveloper.setQualification(30);

        when(developerService.getById(DEVELOPER_ID)).thenReturn(oldDeveloper);

        mockMvc.perform(post("/developers/update")
                .param("developer_id", String.valueOf(DEVELOPER_ID))
                .param("name", NAME)
                .param("rank", String.valueOf(RANK))
                .param("experience", String.valueOf(EXPERIENCE))
                .param("qualification", String.valueOf(QUALIFICATION)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/developers"));

        ArgumentCaptor<Developer> boundDeveloper = ArgumentCaptor.forClass(Developer.class);
        verify(developerService).updateDeveloper(boundDeveloper.capture());
        assertEquals(DEVELOPER_ID, boundDeveloper.getValue().getId());
        assertEquals(NAME, boundDeveloper.getValue().getName());
        assertEquals(RANK, boundDeveloper.getValue().getRank());
        assertEquals(EXPERIENCE, boundDeveloper.getValue().getExperience(), DELTA);
        assertEquals(QUALIFICATION, boundDeveloper.getValue().getQualification());
    }

    @Test
    public void edit() throws Exception {

        when(developerService.getById(DEVELOPER_ID)).thenReturn(new Developer());

        mockMvc.perform(get("/developers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("developers/edit"))
                .andExpect(model().attribute("developer", instanceOf(Developer.class)));
    }

    @Test
    public void assign() throws Exception {
        Developer developer = new Developer();
        developer.setId(DEVELOPER_ID);
        Task task = new Task();
        task.setId(TASK_ID);

        when(developerService.getById(DEVELOPER_ID)).thenReturn(developer);
        when(taskService.getById(TASK_ID)).thenReturn(task);

        mockMvc.perform(post("/developers/assign")
                .param("developer_id", String.valueOf(DEVELOPER_ID))
                .param("task_id", String.valueOf(TASK_ID)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/developers"));

        verify(developerService, times(1)).assignTaskDirectly(developer, task);
    }

    @Test
    public void assignTask() throws Exception {
        when(developerService.getById(DEVELOPER_ID)).thenReturn(new Developer());

        mockMvc.perform(get("/developers/1/assignTask"))
                .andExpect(status().isOk())
                .andExpect(view().name("developers/assignTask"))
                .andExpect(model().attribute("developer", instanceOf(Developer.class)))
                .andExpect(model().attribute("allTasks", taskService.showAllTasks()));
    }

    @Test
    public void listAssignedTasks() throws Exception {
        Developer developer = new Developer();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        developer.setAssignedTasks(tasks);

        when(developerService.getById(DEVELOPER_ID)).thenReturn(developer);

        mockMvc.perform(get("/developers/1/showTasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("developers/assignedTasks"))
                .andExpect(model().attribute("assignedTasks", hasSize(2)));
    }

    @Test
    public void cancelTask() throws Exception {
        Developer developer = new Developer();
        developer.setId(DEVELOPER_ID);
        Task task = new Task();
        task.setStatus(Status.NOT_ASSIGNED);
        task.setId(TASK_ID);

        when(developerService.getById(DEVELOPER_ID)).thenReturn(developer);
        when(taskService.getById(TASK_ID)).thenReturn(task);
        //verifies that error page occurs, as far as the task is not assigned to anyone
        mockMvc.perform(get("/developers/1/showTasks/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403"));
    }

    @Test
    public void cancelTask2() throws Exception {
        Developer developer = new Developer();
        developer.setId(DEVELOPER_ID);
        Task task = new Task();
        task.setStatus(Status.ASSIGNED);
        task.setId(TASK_ID);

        when(developerService.getById(DEVELOPER_ID)).thenReturn(developer);
        when(taskService.getById(TASK_ID)).thenReturn(task);
        //verifies that error page occurs, as far as the task is not assigned to anyone
        mockMvc.perform(get("/developers/1/showTasks/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/developers"));

        verify(developerService, times(1)).cancelExecuting(developer, task);
        verify(developerService, times(1)).updateDeveloper(developer);
        verify(taskService, times(1)).updateTask(task);
    }

}