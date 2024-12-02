package com.sahabuddin.contactmanager.controllers.rest;


import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.respositories.ContactRepository;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;

    private final ContactRepository contactRepository;


    @GetMapping(value = "/user")
    List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/contact")
    List<Contact> getAllContact(){
        return contactRepository.findAll();
    }
}
