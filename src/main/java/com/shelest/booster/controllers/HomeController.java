package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.DeveloperExistsException;
import com.shelest.booster.utilities.State;
import com.shelest.booster.utilities.Status;
import com.shelest.booster.utilities.dto.DeveloperDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        DeveloperDTO developerDTO = new DeveloperDTO();
        model.addAttribute("user", developerDTO);
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid DeveloperDTO accountDto,
             BindingResult result, WebRequest request, Errors errors) {
        Developer registered = new Developer();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("name", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registerError", "user", accountDto);
        }
        else {
            return new ModelAndView("successRegister", "user", accountDto);
        }
    }
    private Developer createUserAccount(DeveloperDTO accountDto, BindingResult result) {
        Developer registered = null;
        try {
            registered = developerService.registerNewDeveloperAccount(accountDto);
        } catch (DeveloperExistsException e) {
            return null;
        }
        return registered;
    }
}
