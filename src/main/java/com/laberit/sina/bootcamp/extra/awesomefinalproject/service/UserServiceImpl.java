package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean registerUser(String username, String password, String name, String surnames, Role role){
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodePassword(password));
        newUser.setName(name);
        newUser.setSurnames(surnames);
        newUser.setRole(role);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
