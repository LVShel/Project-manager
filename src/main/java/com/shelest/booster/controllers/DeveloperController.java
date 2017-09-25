package com.shelest.booster.controllers;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Home on 25.09.2017.
 */
@Controller
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepository;

    @RequestMapping(value="", method=RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("developers", developerRepository.findAll());
        return "developers/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        developerRepository.delete(id);
        return new ModelAndView("redirect:/developers");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "developers/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name) {
        developerRepository.save(new Developer(name));
        return new ModelAndView("redirect:/developers");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("developer_id") long id,
                               @RequestParam("name") String name) {
        Developer developer = developerRepository.findOne(id);
        developer.setName(name);
        developerRepository.save(developer);
        return new ModelAndView("redirect:/developers");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Developer developer = developerRepository.findOne(id);
        model.addAttribute("developer", developer);
        return "developers/edit";
    }

}