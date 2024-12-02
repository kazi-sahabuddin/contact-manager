package com.sahabuddin.contactmanager.services.impl;

import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;
import com.sahabuddin.contactmanager.respositories.ContactRepository;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final UserRepository userRepository;

    @Override
    public Contact createContact(ContactRequest request,  User user) {
        Contact contact = new Contact();
        contact.setEmail(request.getEmail());
        contact.setName(request.getName());
        contact.setNickName(request.getNickName());
        contact.setPhone(request.getPhone());
        contact.setDescription(request.getDescription());
        contact.setWork(request.getWork());
        contact.setUser(user);
        return contactRepository.save(contact);
    }
}
