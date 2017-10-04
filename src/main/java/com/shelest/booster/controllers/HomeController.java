package com.shelest.booster.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Home on 26.09.2017.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }
}
