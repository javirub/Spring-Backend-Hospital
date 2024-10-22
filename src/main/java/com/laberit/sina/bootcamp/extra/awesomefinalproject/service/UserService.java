package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;

import java.util.Map;

public interface UserService {
    Map<String, String> changePassword(PasswordDTO passwordDTO, String username);

    Map<String, String> deleteUser(String username);
}