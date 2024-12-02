package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import com.sahabuddin.contactmanager.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    private final ContactService contactService;

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
    public String addContactView(Model model) {
        model.addAttribute("title", "Add Contact | Contact Manager");
        model.addAttribute("contact", new ContactRequest());
        return "user/add_contact";
    }

    @PostMapping(value = "/add-contact")
    public String addContactSave(@Valid @ModelAttribute("contact") ContactRequest request, BindingResult result, Model model, Principal principal) {
        model.addAttribute("title", "Add Contact | Contact Manager");
        log.info("add contact request is {}", request);
        contactService.createContact(request, getUser(principal));
        model.addAttribute("contact", new ContactRequest());
        return "user/add_contact";
    }

    private User getUser( Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}
