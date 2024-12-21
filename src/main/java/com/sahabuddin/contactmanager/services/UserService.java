package com.sahabuddin.contactmanager.services;

import com.sahabuddin.contactmanager.entities.User;

public interface UserService {

    void passwordChange(String oldPassword, String newPassword, User user);

}
