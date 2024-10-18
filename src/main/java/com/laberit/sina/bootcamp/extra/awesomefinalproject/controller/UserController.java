package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    @ResponseBody
    public User registerPost(@RequestBody @Valid UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userService.registerUser(userDTO);
    }

    @PutMapping("/change_password")
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO) {
        if (passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            passwordDTO.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
            boolean isChanged = userService.changePassword(passwordDTO.getPassword());
            if (isChanged) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password change failed");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
    }
}