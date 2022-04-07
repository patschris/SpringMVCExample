package com.in28minutes.service;

import com.in28minutes.entities.Users;
import com.in28minutes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users findUserByUsername (String username) {
        return userRepository.findUserByUsername(username);
    }
}