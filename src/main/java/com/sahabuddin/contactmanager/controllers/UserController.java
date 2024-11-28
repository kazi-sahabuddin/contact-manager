package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping(value = "/index")
    public String userDashboard(Model model, Principal principal) {
        model.addAttribute("title", "User Dashboard | Contact Manager");
        String username = principal.getName();
        log.info("username is {}", username);
        User user = userRepository.findByEmail(username);
        log.info("user is {}", user);
        model.addAttribute("me", user);
        return "user/user_dashboard";
    }
}
