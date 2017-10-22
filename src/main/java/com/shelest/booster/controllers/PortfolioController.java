package com.shelest.booster.controllers;

import com.shelest.booster.domain.Portfolio;
import com.shelest.booster.services.ManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class PortfolioController {

    @Autowired
    private ManagementService managementService;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/tasksHistory", method = RequestMethod.GET)
    public ModelAndView listTasksHistory(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("portfolio/tasksHistory");
        Portfolio portfolio = managementService.findByNameEquals(authentication.getName()).getPortfolio();
        modelAndView.addObject("tasks", portfolio.getPerformedTasks());
        logger.debug("in GET method listTasksHistory(): when called, found : {}", portfolio.getPerformedTasks().size() + " tasks");
        return modelAndView;
    }
}
