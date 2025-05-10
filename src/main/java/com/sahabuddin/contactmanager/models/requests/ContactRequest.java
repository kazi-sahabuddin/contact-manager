package com.sahabuddin.contactmanager.models.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactRequest {

    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String phone;

    private String work;

    private String description;

    private String imageUrl;
}
