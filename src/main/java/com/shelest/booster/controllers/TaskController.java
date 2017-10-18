package com.shelest.booster.controllers;

import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.Pager;
import com.shelest.booster.utilities.Status;
import com.shelest.booster.utilities.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 12;
    private static final int[] PAGE_SIZES = {5, 8, 12};


    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ModelAndView listAllTasks(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                     @RequestParam(value = "page", required = false) Optional<Integer> page,
                                     @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("tasks/allTasks");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Task> tasks = taskService.showAllTasks(evalPage, evalPageSize, order);
        logger.debug(" in GET method listAllTasks(): showAllTasks(Pageable) is called and found:{}", tasks.getSize() + "tasks");
        Pager pager = new Pager(tasks.getTotalPages(), tasks.getNumber(), BUTTONS_TO_SHOW);
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("order", order);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @RequestMapping(value = "/notAssignedTasks", method = RequestMethod.GET)
    public ModelAndView listNotAssignedTasks(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                             @RequestParam(value = "page", required = false) Optional<Integer> page,
                                             @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("tasks/notAssignedTasks");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Task> tasks = taskService.showAllTasksByStatus(evalPage, evalPageSize, order, Status.NOT_ASSIGNED);
        logger.debug(" in GET method listNotAssignedTasks(): showAllTasksByStatus(Pageable) is called and found:{}", tasks.getSize() + "tasks");
        Pager pager = new Pager(tasks.getTotalPages(), tasks.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("order", order);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/deleteTask", method = RequestMethod.GET)
    public ModelAndView deleteTask(@PathVariable long id) {
        Task task = taskService.getById(id);
        if (task.getStatus().equals(Status.NOT_ASSIGNED)) {
            taskService.removeTask(id);
            logger.debug(" in GET method deleteTask(): Removed task with ID:{}", id);
        } else {
            logger.error(" in GET method deleteTask(): Attempted to delete already assigned task");
            return new ModelAndView("error/cannotDeleteAssignedTask");
        }
        return new ModelAndView("redirect:/tasks/allTasks");
    }

    @RequestMapping(value = "/newTask", method = RequestMethod.GET)
    public ModelAndView newTask() {
        ModelAndView modelAndView = new ModelAndView("tasks/newTask");
        List<String> projectNames = projectService.showAllProjects()
                .stream().map(Project::getName).collect(Collectors.toList());
        modelAndView.addObject("projectNames", projectNames);
        return modelAndView;
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("projectName") String projectName,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                               @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                               @RequestParam("storyPoints") int storyPoints, Model model) {
        Task task = new Task();
        task.setProjectName(projectName);
        task.setTaskType(taskType);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStoryPoints(storyPoints);
        taskService.addTask(task);
        logger.debug(" in POST method newDeveloper(): Created and dded new task of type:{}", taskType);
        return new ModelAndView("redirect:/tasks/allTasks");
    }

    @RequestMapping(value = "/{id}/editTask", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("tasks/editTask");
        Task task = taskService.getById(id);
        List<String> projectNames = projectService.showAllProjects()
                .stream().map(Project::getName).collect(Collectors.toList());
        modelAndView.addObject("projectNames", projectNames);
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    @RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("task_id") long id,
                               @RequestParam("projectName") String projectName,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                               @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                               @RequestParam("storyPoints") int storyPoints, Model model) {
        Task task = taskService.getById(id);
        task.setProjectName(projectName);
        task.setTaskType(taskType);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStoryPoints(storyPoints);
        taskService.updateTask(task);
        logger.debug(" in POST method update(): Updated task with ID:{}", id);
        return new ModelAndView("redirect:/tasks/allTasks");
    }
}
