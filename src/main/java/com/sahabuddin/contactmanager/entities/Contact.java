package com.sahabuddin.contactmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CONTACTS")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String phone;

    private String work;

    @Column(length = 1000)
    private String description;

    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    private User user;

}
