package com.sahabuddin.contactmanager.services;

import com.sahabuddin.contactmanager.entities.Contact;
import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.models.requests.ContactRequest;

public interface ContactService {

    Contact createContact(ContactRequest request, User user);
}
