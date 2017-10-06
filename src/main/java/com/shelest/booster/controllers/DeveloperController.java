package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Task;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.Rank;
import com.shelest.booster.utilities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ManagementService managementService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listDevelopers(Model model) {
        model.addAttribute("developers", developerService.showAllDevelopers());
        return "developers/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        developerService.removeDeveloper(id);
        return new ModelAndView("redirect:/developers");
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
        return new ModelAndView("redirect:/developers");
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
        return new ModelAndView("redirect:/developers");
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
        return new ModelAndView("redirect:/developers");
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
        return new ModelAndView("redirect:/developers");
    }
}