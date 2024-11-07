package com.sahabuddin.contactmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(length = 500)
    private String about;

    private String role;

    private Boolean enabled;

    private String imageUrl;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Contact> contacts = new ArrayList<>();
}
