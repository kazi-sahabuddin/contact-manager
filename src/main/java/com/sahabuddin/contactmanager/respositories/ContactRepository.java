package com.sahabuddin.contactmanager.respositories;

import com.sahabuddin.contactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
