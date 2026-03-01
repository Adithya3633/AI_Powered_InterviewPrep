package com.Portal.AI_Interview_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Portal.AI_Interview_Portal.entity.user;
import com.Portal.AI_Interview_Portal.repository.UserRepository;

@Service
public class userservice {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(user user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public user findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	
}
