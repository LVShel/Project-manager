package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Manager;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.enums.EstimationStatus;
import com.shelest.booster.utilities.Pager;
import com.shelest.booster.utilities.enums.ExecutionStatus;
import com.shelest.booster.utilities.enums.Status;
import com.shelest.booster.utilities.enums.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ManagementService managementService;

    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 8;
    private static final int[] PAGE_SIZES = {5, 8, 12};


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ModelAndView listAllTasks(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                     @RequestParam(value = "page", required = false) Optional<Integer> page,
                                     @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("tasks/allTasks");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Task> tasks = taskService.showAllTaskByStatusIsNot(evalPage, evalPageSize, order, Status.CLOSED);
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/doneTasks", method = RequestMethod.GET)
    public ModelAndView listDoneTasks(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                             @RequestParam(value = "page", required = false) Optional<Integer> page,
                                             @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("tasks/doneTasks");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Task> tasks = taskService.showAllTasksByExecutionStatusAndStatus(evalPage, evalPageSize, order, ExecutionStatus.DONE, Status.ASSIGNED);
        logger.debug(" in GET method listDoneTasks(): showAllTasksByExecutionStatus(Pageable) is called and found:{}", tasks.getTotalElements()+ "tasks");
        Pager pager = new Pager(tasks.getTotalPages(), tasks.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("order", order);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/newTask", method = RequestMethod.GET)
    public ModelAndView newTask() {
        ModelAndView modelAndView = new ModelAndView("tasks/newTask");
        List<String> projectNames = projectService.showAllProjects()
                .stream().map(Project::getName).collect(Collectors.toList());
        modelAndView.addObject("projectNames", projectNames);
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("projectName") String projectName,
                               @RequestParam("task_descr") String taskDescr,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                               @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        int difference = startDate.compareTo(endDate);
        if(taskDescr.length() <= 300 & difference <= 0){
            Task task = new Task();
            task.setProjectName(projectName);
            task.setTaskType(taskType);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            task.setDescription(taskDescr);
            taskService.addTask(task);
        }else {
            if(taskDescr.length() > 300){
                logger.error("Tried to add task description more than 300 length");
                return new ModelAndView("error/descrLength");
            }
            if(difference > 0){
                logger.error("Tried to choose invalid endDate");
                return new ModelAndView("error/dateDifference");
            }
        }
        logger.debug(" in POST method newDeveloper(): Created and dded new task of type:{}", taskType);
        return new ModelAndView("redirect:/tasks/allTasks");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("task_id") long id,
                               @RequestParam("projectName") String projectName,
                               @RequestParam("task_descr") String taskDescr,
                               @RequestParam("taskType") TaskType taskType,
                               @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                               @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        int difference = startDate.compareTo(endDate);
        if(taskDescr.length() > 300 & difference <= 0){
            Task task = taskService.getById(id);
            task.setProjectName(projectName);
            task.setTaskType(taskType);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            task.setDescription(taskDescr);
            taskService.updateTask(task);
        }else {
            if (taskDescr.length() > 300) {
                logger.error("Tried to add task description more than 300 length");
                return new ModelAndView("error/descrLength");
            }
            if (difference > 0) {
                logger.error("Tried to choose invalid endDate");
                return new ModelAndView("error/dateDifference");
            }
        }
        logger.debug(" in POST method update(): Updated task with ID:{}", id);
        return new ModelAndView("redirect:/tasks/allTasks");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/{id}/estimate", method = RequestMethod.GET)
    public ModelAndView estimateTask(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("tasks/estimate");
        Task task = taskService.getById(id);
        modelAndView.addObject("task", task);//todo create whitelabel error page handler
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/estimate", method = RequestMethod.POST)
    public ModelAndView estimate(@RequestParam("task_id") long id,
                                 @RequestParam("estimation") int estimation) {
        if(estimation > 0 & estimation < 100){
            Task task = taskService.getById(id);
            task.getEstimations().add(estimation);
            task.setEstimationStatus(EstimationStatus.ESTIMATED);
            logger.debug(" in POST method estimate(): estimated task with ID:{}", id);
            taskService.updateTask(task);
            List<Integer> estimations = task.getEstimations();
            int storyPointsAvg = (int) estimations.stream().mapToInt((x) -> x).summaryStatistics().getAverage();
            task.setStoryPoints(storyPointsAvg);
            taskService.updateTask(task);
        }else {
            logger.error("Tried to choose invalid endDate");
            return new ModelAndView("error/estimError");
        }

        logger.debug(" in POST method estimate(): updated story points for  task with ID:{}", id);
        return new ModelAndView("redirect:/tasks/notAssignedTasks");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/myTasks", method = RequestMethod.GET)
    public ModelAndView listMyTasks(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("tasks/myTasks");
        Developer developer = developerService.getByNameEquals(authentication.getName());
        modelAndView.addObject("developer", developer);
        logger.debug("in Get method listMyTasks(Authentication) found developer with ID: {}", developer.getId());
        modelAndView.addObject("myTasks", developer.getAssignedTasks());
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/{id}/markAsDone", method = RequestMethod.GET)
    public ModelAndView markAsDone(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/tasks/myTasks");
        Task task = taskService.getById(id);
        task.setExecutionStatus(ExecutionStatus.DONE);
        taskService.updateTask(task);
        logger.debug(" in GET method markAsDone(): Updated task with ID:{}", id);
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}/closeTask", method = RequestMethod.GET)
    public ModelAndView closeTask(@PathVariable long id, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/tasks/doneTasks");
        Task task = taskService.getById(id);
        Developer developer = developerService.getById(task.getExecutorID());
        managementService.cancelExecuting(developer, task);
        task.setStatus(Status.CLOSED);
        logger.debug(" in GET method closeTask(): closed and moved to portfolio task with ID: {}", id);
        taskService.updateTask(task);
        developerService.updateDeveloper(developer);
        Manager manager = managementService.findByNameEquals(authentication.getName());
        manager.getPortfolio().getPerformedTasks().add(task);
        managementService.update(manager);
        logger.debug(" in GET method closeTask(): Updated manager");
        return modelAndView;
    }
}
