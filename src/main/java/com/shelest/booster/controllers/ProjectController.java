package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ManagementService managementService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listProjects() {
        ModelAndView modelAndView = new ModelAndView("projects/projectList");
        modelAndView.addObject("projects", projectService.showAllProjects());
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/deleteProject", method = RequestMethod.GET)
    public ModelAndView deleteProject(@PathVariable long id) {
        projectService.removeProject(id);
        return new ModelAndView("redirect:/projects");
    }

    @RequestMapping(value = "/newProject", method = RequestMethod.GET)
    public ModelAndView newProject() {
        return new ModelAndView("projects/newProject");
    }

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("project_name") String name,
                               @RequestParam("seniorsNeed") int seniorsNeed,
                               @RequestParam("middlesNeed") int middlesNeed,
                               @RequestParam("juniorsNeed") int juniorsNeed,
                               @RequestParam("maxTasks") int maxTasksForOne, Model model) {
        Project project = new Project();
        project.setName(name);
        project.setSeniorsNeed(seniorsNeed);
        project.setMiddlesNeed(middlesNeed);
        project.setJuniorsNeed(juniorsNeed);
        project.setMaxTasksForOneDev(maxTasksForOne);
        projectService.addProject(project);

        return new ModelAndView("redirect:/projects");
    }

    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("project_id") long id,
                               @RequestParam("project_name") String name,
                               @RequestParam("seniorsNeed") int seniorsNeed,
                               @RequestParam("middlesNeed") int middlesNeed,
                               @RequestParam("juniorsNeed") int juniorsNeed,
                               @RequestParam("maxTasks") int maxTasksForOne, Model model) {
        Project project = projectService.getById(id);
        project.setName(name);
        project.setSeniorsNeed(seniorsNeed);
        project.setMiddlesNeed(middlesNeed);
        project.setJuniorsNeed(juniorsNeed);
        project.setMaxTasksForOneDev(maxTasksForOne);
        projectService.updateProject(project);
        return new ModelAndView("redirect:/projects");
    }

    @RequestMapping(value = "/{id}/editProject", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("projects/editProject");
        Project project = projectService.getById(id);
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/showTeam", method = RequestMethod.GET)
    public ModelAndView listAssignedDevelopers(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("projects/projectTeam");
        modelAndView.addObject("developersOnProject", projectService.getById(id).getDevelopersOnProject());
        modelAndView.addObject("project", projectService.getById(id));
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/showTeam/{devId}", method = RequestMethod.GET)
    public ModelAndView cancelTask(@PathVariable(value = "id") long id,
                                   @PathVariable(value = "devId") long devId, Model model) {
        Project project = projectService.getById(id);
        Developer developer = developerService.getById(devId);
        model.addAttribute("team", projectService.getById(id).getDevelopersOnProject());
        model.addAttribute("developer", developerService.getById(id));
        managementService.removeDeveloperFromProject(developer, project);
        projectService.updateProject(project);
        return new ModelAndView("redirect:/projects");
    }

}
