package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> changePassword(PasswordDTO passwordDTO, String username);

    ResponseEntity<String> deleteUser(String username);
}