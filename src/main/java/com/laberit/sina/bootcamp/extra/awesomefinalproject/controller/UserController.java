package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public User registerPost(@Valid String username, String password, String name, String surnames, Role role) {
        if (userService.registerUser(username, password, name, surnames, role)) {
            return new User(username, password, name, surnames, role);
        } else {
            return null;
        }
    }
}
