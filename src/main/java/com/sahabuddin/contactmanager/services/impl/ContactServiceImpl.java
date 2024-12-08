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
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
            File savedFile = new ClassPathResource("static/uploaded").getFile();
            log.info("path: {}", savedFile);
            Path path = Paths.get(savedFile + File.separator + file.getOriginalFilename());
            File fileNew = new File(String.valueOf(path));
            File directory = fileNew.getParentFile();

            if (directory != null && !directory.exists()) {
                if (directory.mkdirs()) {
                    log.info("Directory created successfully.");
                } else {
                    log.info("Failed to create directory.");
                }
            }
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        }else{
            contact.setImageUrl("contact.png");
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

    @Override
    public Page<Contact> getAllContactByUser(User user, Pageable pageable) {
        return contactRepository.findAllByUser(user, pageable);
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }
}
