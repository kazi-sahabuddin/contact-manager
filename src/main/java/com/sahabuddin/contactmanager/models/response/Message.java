package com.sahabuddin.contactmanager.models.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable {

    private String content;
    private String type;
}
