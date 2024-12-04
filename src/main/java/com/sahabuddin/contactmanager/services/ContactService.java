package com.sahabuddin.contactmanager.services;

import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContactService {

    Contact createContact(ContactRequest request, User user, MultipartFile file) throws IOException;

    List<Contact> getAllContactByUser(User user);
}
