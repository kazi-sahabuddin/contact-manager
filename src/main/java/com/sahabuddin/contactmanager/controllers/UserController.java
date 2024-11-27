package com.sahabuddin.contactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping(value = "/index")
    public String userDashboard(Model model) {
        model.addAttribute("title", "User Dashboard | Contact Manager");
        return "user/user_dashboard";
    }
}
