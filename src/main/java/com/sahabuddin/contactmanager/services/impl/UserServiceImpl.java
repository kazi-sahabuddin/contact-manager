package com.sahabuddin.contactmanager.services.impl;

import com.sahabuddin.contactmanager.entities.User;
import com.sahabuddin.contactmanager.respositories.UserRepository;
import com.sahabuddin.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void passwordChange(String oldPassword, String newPassword, User user) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
    }
}
