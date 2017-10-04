package com.shelest.booster.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Home on 28.09.2017.
 */
@Controller
public class SecurityController {
    @GetMapping("/1")
    public String home1() {
        return "/home1";
    }

    @GetMapping("/home1")
    public String home() {
        return "/home1";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
