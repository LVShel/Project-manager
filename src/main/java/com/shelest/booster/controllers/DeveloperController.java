package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.Pager;
import com.shelest.booster.utilities.Rank;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.Status;
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
        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);

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
        model.addAttribute("rank", rank);
        return new ModelAndView("redirect:/developers/bench");
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
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("developer", developer);
        return "developers/edit";
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public ModelAndView assign(@RequestParam("developer_id") long developerId,
                               @RequestParam("task_id") long taskId) {
        Developer developer = developerService.getById(developerId);
        Task task = taskService.getById(taskId);
        if (task.getStatus() != Status.ASSIGNED) {
            managementService.assignTask(developer, task);
            developerService.updateDeveloper(developer);
        } else {
            return new ModelAndView("error/alreadyAssigned");
        }
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/assignTask", method = RequestMethod.GET)
    public String assignTask(@PathVariable long id, Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("allTasks", taskService.showAllTasks());
        model.addAttribute("developer", developer);
        return "developers/assignTask";
    }

    @RequestMapping(value = "/{id}/showTasks", method = RequestMethod.GET)
    public String listAssignedTasks(@PathVariable long id, Model model) {
        model.addAttribute("assignedTasks", developerService.getById(id).getAssignedTasks());
        model.addAttribute("developer", developerService.getById(id));
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
            developerService.updateDeveloper(developer);
            taskService.updateTask(task);
        } else {
            return new ModelAndView("error/403");
        }
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/assignToProject", method = RequestMethod.POST)
    public ModelAndView assignToProject(@RequestParam("developer_id") long developerId,
                               @RequestParam("project_id") long projectId) {
        Developer developer = developerService.getById(developerId);
        Project project = projectService.getById(projectId);
        managementService.assignDeveloperToProject(developer, project);
        projectService.updateProject(project);
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/{id}/assignToProject", method = RequestMethod.GET)
    public String assignProject(@PathVariable long id, Model model) {
        Developer developer = developerService.getById(id);
        model.addAttribute("developer", developer);
        model.addAttribute("projects", projectService.showAllProjects());
        return "developers/assignToProject";
    }

    @RequestMapping(value = "/assignAllTasks", method = RequestMethod.GET)
    public ModelAndView assignAllTasks() {
        List<Task> notAssignedTasks = taskService.getByStatus(Status.NOT_ASSIGNED);
        List<Project> projects = projectService.showAllProjects();

        managementService.assignAllTasks(projects, notAssignedTasks);
        developerService.updateAllDevelopers();
        taskService.updateAllTasks();
        projectService.updateAllProjects();
        return new ModelAndView("redirect:/developers/bench");
    }

    @RequestMapping(value = "/cancelAllTasks", method = RequestMethod.GET)
    public ModelAndView cancelAllTasks() {
        List<Task> assignedTasks = taskService.getByStatus(Status.ASSIGNED);
        List<Project> projects = projectService.showAllProjects();

        managementService.cancelAllTasks(projects, assignedTasks);
        developerService.updateAllDevelopers();
        taskService.updateAllTasks();
        projectService.updateAllProjects();
        return new ModelAndView("redirect:/developers/bench");
    }
}