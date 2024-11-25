package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.helper.Message;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

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
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(value = "/do-register")
    public String userSignup(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
        try{
            if (!agreement) {
                log.info("Agreement not available");
            }
            model.addAttribute("title", "Sign up | Contact Manager");
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            User saved = userRepository.save(user);
            log.info("User registered successfully {}", saved);
            log.info("user info: {}", user.toString());
            log.info("agreement: {}", agreement);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Registration successfully !", "alert-success"));


        } catch (Exception e){
            model.addAttribute("title", "Sign up | Contact Manager");
            model.addAttribute("user", new User());
            model.addAttribute("error", e.getMessage());
            log.error("error: {}", e.getMessage());
            session.setAttribute("message", new Message("Something went wrong!"+e.getMessage(), "alert-error"));
        }
        return "signup";
    }
}
