package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    @ModelAttribute
    public void commonAttributes(Model model, Principal principal) {
        log.info("user is {}", principal.getName());
        model.addAttribute("user", getUser(principal));
    }


    @GetMapping(value = "/index")
    public String userDashboard(Model model) {
        model.addAttribute("title", "User Dashboard | Contact Manager");
        return "user/user_dashboard";
    }

    @GetMapping(value = "/add-contact")
    public String addContact(Model model) {
        model.addAttribute("title", "Add Contact | Contact Manager");
        model.addAttribute("contact", new ContactRequest());
        return "user/add_contact";
    }

    private User getUser( Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}
