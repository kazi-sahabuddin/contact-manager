package com.sahabuddin.contactmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class ContactManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagerApplication.class, args);
    }

}
