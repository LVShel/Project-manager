package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.domain.Project;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listProjects() {
        ModelAndView modelAndView = new ModelAndView("projects/projectList");
        modelAndView.addObject("projects", projectService.showAllProjects());
        logger.debug("ProjectController in GET method listProjects(): when called, found : {}", projectService.showAllProjects().size() + " projects");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/deleteProject", method = RequestMethod.GET)
    public ModelAndView deleteProject(@PathVariable long id) {
        projectService.removeProject(id);
        logger.debug("ProjectController in GET method deleteProject(): when called, deleted project with ID : {}", id);
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
                               @RequestParam("maxTasks") int maxTasksForOne) {
        Project project = new Project();
        project.setName(name);
        project.setSeniorsNeed(seniorsNeed);
        project.setMiddlesNeed(middlesNeed);
        project.setJuniorsNeed(juniorsNeed);
        project.setMaxTasksForOneDev(maxTasksForOne);
        projectService.addProject(project);
        logger.debug(" in POST method create(): Created and added new project with name : {}", name);
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
        logger.debug(" in POST method update(): updated project with ID : {}", id);
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
        logger.debug(" in GET method listAssignedDevelopers(): found : {}" ,
                projectService.getById(id).getDevelopersOnProject().size()+ " developers on project with ID {}: ", id);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/showTeam/{devId}", method = RequestMethod.GET)
    public ModelAndView removeDeveloper(@PathVariable(value = "id") long id,
                                        @PathVariable(value = "devId") long devId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/projects");
        Project project = projectService.getById(id);
        Developer developer = developerService.getById(devId);
        modelAndView.addObject("team", projectService.getById(id).getDevelopersOnProject());
        modelAndView.addObject("developer", developerService.getById(id));
        managementService.removeDeveloperFromProject(developer, project);
        logger.debug(" in GET method removeDeveloper(): Removed developer with ID: {}", devId+ "from project with ID: {}", id);
        projectService.updateProject(project);
        return modelAndView;
    }

}
