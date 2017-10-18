package com.shelest.booster.controllers;

import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class HomeController {
    @Autowired
    DeveloperService developerService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping("/")
    public ModelAndView greeting() {
        ModelAndView modelAndView = new ModelAndView("home");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d.M.yyyy H:mm:ss");
        String now = LocalDateTime.now().format(format);
        modelAndView.addObject("totalStaffSize", developerService.showAllDevelopers().size());
        modelAndView.addObject("benchSize", developerService.getByState(State.ON_BENCH).size());
        modelAndView.addObject("allProjectsSize", projectService.showAllProjects().size());
        modelAndView.addObject("allTasksSize", taskService.showAllTasks().size());
        modelAndView.addObject("unassignedTasksSize", taskService.getByStatus(Status.NOT_ASSIGNED).size());
        modelAndView.addObject("dateTime", now);
        modelAndView.addObject("free_developers", developerService.getByNumberOfTasks(0).size());
        logger.debug(" in method greeting: home page called");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
