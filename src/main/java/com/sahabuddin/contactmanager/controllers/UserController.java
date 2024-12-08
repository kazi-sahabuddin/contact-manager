package com.sahabuddin.contactmanager.controllers;

import com.sahabuddin.contactmanager.configs.AppProperties;
import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import com.sahabuddin.contactmanager.models.response.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static com.sahabuddin.contactmanager.constants.AppConstant.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    private final ContactService contactService;

    private final AppProperties appProperties;

    @ModelAttribute
    public void commonAttributes(Model model, Principal principal) {
        log.info("user is {}", principal.getName());
        model.addAttribute("user", getUser(principal));
    }


    @GetMapping(value = "/index")
    public String userDashboard(Model model) {
        model.addAttribute(TITLE, "User Dashboard | Contact Manager");
        return "user/user_dashboard";
    }

    @GetMapping(value = "/add-contact")
    public String addContactView(Model model) {
        model.addAttribute(TITLE, "Add Contact | Contact Manager");
        model.addAttribute("contact", new ContactRequest());
        return "user/add_contact";
    }

    @PostMapping(value = "/add-contact")
    public String addContactSave(@Valid @ModelAttribute("contact") ContactRequest request, BindingResult result, @RequestParam("imageFile")MultipartFile file, Model model, Principal principal) {
        model.addAttribute(TITLE, "Add Contact | Contact Manager");
        log.info("add contact request is {}", request);
        try{
            contactService.createContact(request, getUser(principal), file);
            model.addAttribute("message", new Message("Contact added successfully! ", "alert-success"));
        } catch (Exception e) {
            model.addAttribute("message", new Message("Something went wrong "+e.getMessage(), "alert-danger"));
        }
        model.addAttribute("contact", new ContactRequest());

        return "user/add_contact";
    }

    @GetMapping(value = "/view-contacts")
    public String viewContacts(
            @RequestParam(value = PAGE_NO, required = false, defaultValue = PAGE) int pageNo,
            @RequestParam(value = PAGE_SIZE, required = false, defaultValue = SIZE) int pageSize,
            Model model, Principal principal) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
         Page<Contact> pages = contactService.getAllContactByUser(getUser(principal), pageable);
        model.addAttribute(TITLE, "View Contact | Contact Manager");
        model.addAttribute("dataList",pages );
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("imageBasePath", appProperties.getFilePath());
        return "user/view_contacts";
    }

    @GetMapping(value = "/{id}/contact")
    public String detailsContact(@PathVariable Long id, Model model, Principal principal) {

        Contact contact = contactService.getContactById(id);
        model.addAttribute(TITLE, "View Contact | Contact Manager");
        model.addAttribute("contact",contact );

        return "user/details_contact";
    }

    private User getUser( Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}
