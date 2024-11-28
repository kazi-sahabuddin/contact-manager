package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.helper.Message;
import com.sahabuddin.contactmanager.models.requests.SignInRequest;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = {"/","/home"})
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
    public String signup(Model model, HttpSession session) {
        session.removeAttribute("message");
        model.addAttribute("title", "Sign up | Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String userSignup(@Valid @ModelAttribute("user") User user,BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,  Model model, HttpSession session) {
        session.removeAttribute("message");
        try{
            if (!agreement) {
                log.error("You have not agreed the terms and conditions");
                throw new IllegalArgumentException("You have not agreed the terms and conditions");
            }

            if (result.hasErrors()) {
                log.error("Error: {}", result.toString());
                model.addAttribute("title", "Sign up | Contact Manager");
                model.addAttribute("user", user);
                model.addAttribute("isAgreed", agreement);
                return "signup";
            }

            model.addAttribute("title", "Sign up | Contact Manager");
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("User: {}", user);
            User saved = userRepository.save(user);
            log.info("User registered successfully {}", saved);
            log.info("user info: {}", user.toString());
            log.info("agreement: {}", agreement);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Registration successfully! ", "alert-success"));


        } catch (Exception e){
            model.addAttribute("title", "Sign up | Contact Manager");
            model.addAttribute("user", new User());
            log.error("error: {}", e.getMessage());
            session.setAttribute("message", new Message("Something went wrong! "+e.getMessage(), "alert-danger"));
        }
        return "signup";
    }

    @GetMapping(value = "/sign-in")
    public String singIn(Model model){
        model.addAttribute("title", "Login | Contact Manager");
        return "login";
    }

//    @PostMapping(value = "/sign-in")
//    public String signInPost(@Valid @ModelAttribute("signInRequest") SignInRequest signInRequest, BindingResult result, Model model, HttpSession session ) {
//        session.removeAttribute("message");
//        if (result.hasErrors()) {
//            return "login";
//        }
//        log.info("Sign in request: {}", signInRequest);
//        model.addAttribute("title", "Login | Contact Manager");
//        model.addAttribute("signInRequest", new SignInRequest());
//        return "login";
//
//    }
}
