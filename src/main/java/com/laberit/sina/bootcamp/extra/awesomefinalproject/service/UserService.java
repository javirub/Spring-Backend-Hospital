package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Role;

public interface UserService {
    boolean registerUser(String username, String password, String name, String surnames, Role role);
    String encodePassword(String password);
}
