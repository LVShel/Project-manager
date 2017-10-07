package com.shelest.booster.controllers;

import com.shelest.booster.domain.Project;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.showAllProjects());
        return "projects/projectList";
    }

    @RequestMapping(value = "/{id}/deleteProject", method = RequestMethod.GET)
    public ModelAndView deleteProject(@PathVariable long id) {
        projectService.removeProject(id);
        return new ModelAndView("redirect:/projects");
    }

    @RequestMapping(value = "/newProject", method = RequestMethod.GET)
    public String newProject() {
        return "projects/newProject";
    }

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
//    private long id;
//    private String name;
//    private int seniorsNeed;
//    private int middlesNeed;
//    private int juniorsNeed;
//    private int maxTasksForOneDev;
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
    public String edit(@PathVariable long id,
                       Model model) {
        Project project = projectService.getById(id);
        model.addAttribute("project", project);
        return "projects/editProject";
    }

}
