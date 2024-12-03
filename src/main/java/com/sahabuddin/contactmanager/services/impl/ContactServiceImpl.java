package com.sahabuddin.contactmanager.services.impl;

import com.sahabuddin.contactmanager.configs.AppProperties;
import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.ContactRepository;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final UserRepository userRepository;

    private final AppProperties appProperties;

    @Override
    public Contact createContact(ContactRequest request, User user, MultipartFile file) throws IOException {
        Contact contact = new Contact();
        if (!file.isEmpty()){
            contact.setImageUrl(file.getOriginalFilename());
            String relativePath = appProperties.getFilePath();
            log.info("path: {}", relativePath);
            Path path = Paths.get(relativePath + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        }

        contact.setEmail(request.getEmail());
        contact.setName(request.getName());
        contact.setNickName(request.getNickName());
        contact.setPhone(request.getPhone());
        contact.setDescription(request.getDescription());
        contact.setWork(request.getWork());
        contact.setUser(user);
        Contact savedContact = contactRepository.save(contact);
        user.getContacts().add(savedContact);
        userRepository.save(user);
        return savedContact;

    }
}
