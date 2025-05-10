package com.sahabuddin.contactmanager.controllers.rest;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class SearchController {

    private final ContactService contactService;

    private final UserRepository userRepository;

    @GetMapping(value = "/search/{query}")
    public ResponseEntity<?> search( @PathVariable("query") String query, Principal principal) {
        return ResponseEntity.ok(contactService.searchByName(query, getUser(principal)));
    }
    private User getUser(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}
