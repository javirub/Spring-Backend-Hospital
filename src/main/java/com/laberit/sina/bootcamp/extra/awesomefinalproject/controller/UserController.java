package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
}
