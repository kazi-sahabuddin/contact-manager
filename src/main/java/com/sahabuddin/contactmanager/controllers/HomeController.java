package com.sahabuddin.contactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("title", "Home | Contact Manager");
        return "home";
    }

    @GetMapping(value = "/about")
    public String about(Model model) {
        model.addAttribute("title", "About | Contact Manager");
        return "about";
    }

    @GetMapping(value = "/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Sign up | Contact Manager");
        return "signup";
    }
}
