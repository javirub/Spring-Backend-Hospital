package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity<String> changePassword(PasswordDTO passwordDTO, String username) {
        if (passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User: " + username + " not found");
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User: " + username + " not found");
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}