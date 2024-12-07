package com.sahabuddin.contactmanager.services;

import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ContactService {

    Contact createContact(ContactRequest request, User user, MultipartFile file) throws IOException;

    Page<Contact> getAllContactByUser(User user, Pageable pageable);
}
