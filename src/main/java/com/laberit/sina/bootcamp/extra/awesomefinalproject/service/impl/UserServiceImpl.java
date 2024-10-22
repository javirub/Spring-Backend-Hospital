package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public Map<String, String> changePassword(PasswordDTO passwordDTO, String username) {
        if (!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));

        userRepository.save(user);

        return Map.of("Status", "Password changed successfully");
    }

    @Override
    @Transactional
    public Map<String, String> deleteUser(String username) {
        if (Objects.equals(username, "admin")) {
            throw new IllegalArgumentException("Admin account cannot be deleted");
        }
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
        userRepository.delete(user);
        return Map.of("Status", "User deleted successfully");
    }
}