package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface AdminService {
    ResponseEntity<?> registerUser(UserDTO userDTO);

    ResponseEntity<?> modifyUser(Long id, UserDTO userDTO);

    ResponseEntity<String> deleteUser(Long id);

    ResponseEntity<?> listUsers(Pageable pageable);
}