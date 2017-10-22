package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.services.DeveloperService;
import com.shelest.booster.services.ManagementService;
import com.shelest.booster.services.ProjectService;
import com.shelest.booster.services.TaskService;
import com.shelest.booster.utilities.DeveloperExistsException;
import com.shelest.booster.utilities.enums.EstimationStatus;
import com.shelest.booster.utilities.enums.ExecutionStatus;
import com.shelest.booster.utilities.enums.State;
import com.shelest.booster.utilities.enums.Status;
import com.shelest.booster.utilities.dto.DeveloperDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class HomeController {
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ManagementService managementService;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping("/home")
    public ModelAndView showWorkBench(Authentication auth) {
        ModelAndView modelAndView = new ModelAndView("home");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d.M.yyyy H:mm:ss");
        String now = LocalDateTime.now().format(format);
        modelAndView.addObject("totalStaffSize", developerService.showAllDevelopers().size());
        modelAndView.addObject("benchSize", developerService.getByState(State.ON_BENCH).size());
        modelAndView.addObject("newComers", developerService.getByState(State.NEW_COMER).size());
        modelAndView.addObject("allProjectsSize", projectService.showAllProjects().size());
        modelAndView.addObject("allTasksSize", taskService.getAllByStatusIsNot(Status.CLOSED).size());
        modelAndView.addObject("unassignedTasksSize", taskService.getByStatus(Status.NOT_ASSIGNED).size());
        modelAndView.addObject("notEstimatedTasks", taskService.getByEstimationStatus(EstimationStatus.NOT_ESTIMATED).size());
        modelAndView.addObject("estimatedTasks", taskService.getByEstimationStatus(EstimationStatus.ESTIMATED).size());
        modelAndView.addObject("notDoneTasks", taskService.getByExecutionStatus(ExecutionStatus.NOT_DONE).size());
        modelAndView.addObject("doneTasks", taskService.getByExecutionStatus(ExecutionStatus.DONE).size());
        modelAndView.addObject("dateTime", now);
        modelAndView.addObject("free_developers", developerService.getByNumberOfTasks(0).size());
        modelAndView.addObject("expectingToClose", taskService.showAllTasksByExecutionStatusAndStatus(ExecutionStatus.DONE, Status.ASSIGNED).size());
        modelAndView.addObject("closedTasksArchive", managementService.findByNameEquals(auth.getName()).getPortfolio().getPerformedTasks().size());
        logger.debug(" in method greeting: home page called");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/")
    public String greeting() {
        return "booster";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        DeveloperDTO developerDTO = new DeveloperDTO();
        model.addAttribute("user", developerDTO);
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid DeveloperDTO accountDto,
             BindingResult result) {
        Developer registered = new Developer();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto);
        }
        if (registered == null) {
            result.rejectValue("name", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("error/registerError", "user", accountDto);
        }
        else {
            return new ModelAndView("successRegister", "user", accountDto);
        }
    }
    private Developer createUserAccount(DeveloperDTO accountDto) {
        Developer registered;
        try {
            registered = developerService.registerNewDeveloperAccount(accountDto);
        } catch (DeveloperExistsException e) {
            return null;
        }
        return registered;
    }
}
