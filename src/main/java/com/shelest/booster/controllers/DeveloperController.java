package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private ProjectService projectService;

    private static Logger logger = LoggerFactory.getLogger(DeveloperController.class);

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 12;
    private static final int[] PAGE_SIZES = { 5, 8, 12 };

    @GetMapping("/all")
    public ModelAndView listDevelopers(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                          @RequestParam(value = "page", required = false) Optional<Integer> page,
                                          @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("developers/all");
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Developer> persons = developerService.findAllPageable(evalPage, evalPageSize, order);
        logger.debug("in GET method listDevelopers(): getByState(Pageable) is called and found: {}", persons.getSize() + "developers");
        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
        modelAndView.addObject("persons", persons);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("order", order);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @RequestMapping(value = "/bench", method = RequestMethod.GET)
    public ModelAndView listBench(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                          @RequestParam(value = "page", required = false) Optional<Integer> page,
                                          @RequestParam(value = "order", required = false) String order) {
        ModelAndView modelAndView = new ModelAndView("developers/bench");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Developer> persons = developerService.getByState(evalPage, evalPageSize, order, State.ON_BENCH);
        logger.debug(" in GET method listBench(): getByState(Pageable) is called and found:{}", persons.getSize() + "developers");
        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
        logger.debug(" in GET method listBench(): new pager object created");
        modelAndView.addObject("persons", persons);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("order", order);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        developerService.removeDeveloper(id);
        logger.debug(" in GET method delete(): Removed developer with ID:{}", id);
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDeveloper() {
        return "developers/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name,
                               @RequestParam("rank") Rank rank,
                               @RequestParam("experience") double experience,
                               @RequestParam("qualification") int qualification, Model model) {
        Developer developer = new Developer();
        developer.setName(name);
        developer.setRank(rank);
        developer.setExperience(experience);
        developer.setQualification(qualification);
        developerService.addDeveloper(developer);
        logger.debug(" in POST method newDeveloper(): Created and dded new developer with name:{}", name);
        model.addAttribute("rank", rank);
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("developer", developer);
        return "developers/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("developer_id") long id,
                               @RequestParam("name") String name,
                               @RequestParam("rank") Rank rank,
                               @RequestParam("experience") double experience,
                               @RequestParam("qualification") int qualification) {
        Developer developer = developerService.getById(id);
        developer.setName(name);
        developer.setRank(rank);
        developer.setExperience(experience);
        developer.setQualification(qualification);
        developerService.updateDeveloper(developer);
        logger.debug(" in POST method update(): Updated developer with ID:{}", id);
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/assignTask", method = RequestMethod.GET)
    public String assignTask(@PathVariable long id, Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("allTasks", taskService.getByStatus(Status.NOT_ASSIGNED));
        model.addAttribute("developer", developer);
        return "developers/assignTask";
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public ModelAndView assign(@RequestParam("developer_id") long developerId,
                               @RequestParam("task_id") long taskId) {
        Developer developer = developerService.getById(developerId);
        Task task = taskService.getById(taskId);
        if (task.getStatus() != Status.ASSIGNED) {
            managementService.assignTask(developer, task);
            logger.debug(" in POST method assign(): Assigned task with ID:{}", taskId + "to developer with ID: {}", developerId);
            developerService.updateDeveloper(developer);
            logger.debug(" in POST method assign(): Updated developer with ID:{}", developerId);
        } else {
            logger.error(" in POST method assign(): Attempted to assign already assigned task");
            return new ModelAndView("error/alreadyAssigned");
        }
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/showTasks", method = RequestMethod.GET)
    public String listAssignedTasks(@PathVariable long id, Model model) {
        model.addAttribute("assignedTasks", developerService.getById(id).getAssignedTasks());
        model.addAttribute("developer", developerService.getById(id));
        logger.debug(" in method showTasks():found assigned to developer with ID: {}", id + " tasks: {}", developerService.getById(id).getAssignedTasks().size());
        return "developers/assignedTasks";
    }

    @RequestMapping(value = "/{id}/showTasks/{taskId}", method = RequestMethod.GET)
    public ModelAndView cancelTask(@PathVariable(value = "id") long id,
                                   @PathVariable(value = "taskId") long taskId, Model model) {
        Developer developer = developerService.getById(id);
        Task task = taskService.getById(taskId);
        model.addAttribute("assignedTasks", developerService.getById(id).getAssignedTasks());
        model.addAttribute("developer", developerService.getById(id));
        model.addAttribute("cancelled_task", taskService.getById(taskId));
        if (task.getStatus() == Status.ASSIGNED) {
            managementService.cancelExecuting(developer, task);
            logger.debug(" in GET method cancelTask(): Cancelled task with ID:{}", taskId + "for developer with ID: {}", id);
            developerService.updateDeveloper(developer);
            logger.debug(" in GET method cancelTask(): Updated developer with ID:{}", id);
            taskService.updateTask(task);
            logger.debug(" in GET method cancelTask(): Updated task with ID:{}", taskId);
        } else {
            logger.error(" in GET method cancelTask(): Attempted to cancel not assigned task");
            return new ModelAndView("error/cancelNotAssignedTask");
        }
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/assignToProject", method = RequestMethod.GET)
    public String assignProject(@PathVariable long id, Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("developer", developer);
        model.addAttribute("projects", projectService.showAllProjects());
        return "developers/assignToProject";
    }

    @RequestMapping(value = "/assignToProject", method = RequestMethod.POST)
    public ModelAndView assignToProject(@RequestParam("developer_id") long developerId,
                               @RequestParam("project_id") long projectId) {
        Developer developer = developerService.getById(developerId);
        Project project = projectService.getById(projectId);
        managementService.assignDeveloperToProject(developer, project);
        logger.debug(" in POST method assignToProject(): Assigned developer with ID:{}", developerId + "to project with ID: {}", projectId);
        projectService.updateProject(project);
        logger.debug(" in POST method assignToProject(): Updated project with ID:{}", projectId);
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/assignAllTasks", method = RequestMethod.GET)
    public ModelAndView assignAllTasks() {
        List<Task> notAssignedTasks = taskService.getByStatus(Status.NOT_ASSIGNED);
        List<Project> projects = projectService.showAllProjects();

        managementService.assignAllTasks(projects, notAssignedTasks);
        logger.debug(" in GET method assignAllTasks(): assigned: {}", notAssignedTasks.size() +" tasks among: {}", projects.size()+ " projects");
        developerService.updateAllDevelopers();
        logger.debug(" in GET method assignAllTasks(): updated all developers");
        taskService.updateAllTasks();
        logger.debug(" in GET method assignAllTasks(): updated all tasks");
        projectService.updateAllProjects();
        logger.debug(" in GET method assignAllTasks(): updated all projects");
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/cancelAllTasks", method = RequestMethod.GET)
    public ModelAndView cancelAllTasks() {
        List<Task> assignedTasks = taskService.getByStatus(Status.ASSIGNED);
        List<Project> projects = projectService.showAllProjects();

        managementService.cancelAllTasks(projects, assignedTasks);
        logger.debug(" in GET method cancelAllTasks(): cancelled: {}", assignedTasks.size()+ " tasks among: {}", projects.size()+ " projects");
        developerService.updateAllDevelopers();
        logger.debug(" in GET method cancelAllTasks(): updated all developers");
        taskService.updateAllTasks();
        logger.debug(" in GET method cancelAllTasks(): updated all tasks");
        projectService.updateAllProjects();
        logger.debug(" in GET method cancelAllTasks(): updated all projects");
        return new ModelAndView("redirect:/");
    }
}