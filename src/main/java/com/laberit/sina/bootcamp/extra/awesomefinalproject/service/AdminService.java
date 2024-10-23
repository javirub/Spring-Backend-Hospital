package com.laberit.sina.bootcamp.extra.awesomefinalproject.service;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface AdminService {
    User registerUser(UserDTO userDTO);

    User updateUser(Long id, UserDTO userDTO);

    ResponseEntity<Map<String, Object>> deleteUser(Long id);

    Page<User> listUsers(Pageable pageable, String name, String surnames, String role, String username, List<String> permissions);
}