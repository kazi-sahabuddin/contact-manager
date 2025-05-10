package com.sahabuddin.contactmanager.services.impl;

import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.ContactRepository;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public Contact createContact(ContactRequest request, User user, MultipartFile file) {
        Contact contact = new Contact();
        if (!file.isEmpty()){
            uploadFile(file);
            contact.setImageUrl(file.getOriginalFilename());
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

    @Override
    public Contact getContactByIdAndUser(Long id, User user) {
        return contactRepository.findByIdAndUser(id, user);
    }

    @Override
    public void deleteContactByIdAndUser(Long id, User user) {
        Contact contact = contactRepository.findByIdAndUser(id, user);
        user.getContacts().remove(contact);
        userRepository.save(user);
        contact.setUser(null);
        deleteFileByFileName(contact.getImageUrl());
        contactRepository.delete(contact);
    }

    @Override
    public void updateContact(ContactRequest request, User user, MultipartFile file) {

        Contact contact = contactRepository.findByIdAndUser(request.getId(), user);
        try{
            if (!file.isEmpty()){
               deleteFileByFileName(contact.getImageUrl());
               uploadFile(file);
               contact.setImageUrl(file.getOriginalFilename());
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }

        contact.setEmail(request.getEmail());
        contact.setName(request.getName());
        contact.setNickName(request.getNickName());
        contact.setPhone(request.getPhone());
        contact.setDescription(request.getDescription());
        contact.setWork(request.getWork());
        contactRepository.save(contact);


    }

    @Override
    public List<Contact> searchByName(String query, User user) {
        return contactRepository.findByNameContainingIgnoreCaseAndUser(query, user);
    }

    private void deleteFileByFileName(String fileName) {
        try {
            if (!fileName.equals("contact.png")) {
                File file = new ClassPathResource("static/uploaded").getFile();
                Path path = Paths.get(file + File.separator + fileName);
                if (Files.exists(path)) {
                    Files.delete(path);  // Delete the file
                    log.info("File deleted successfully.");
                } else {
                    log.warn("File not found: {}", path);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void uploadFile(MultipartFile file){
        try {
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
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
