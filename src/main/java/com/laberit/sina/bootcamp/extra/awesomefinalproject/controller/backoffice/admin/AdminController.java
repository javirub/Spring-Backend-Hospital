package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.admin;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserResponseDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for ADMIN management.
 * Allows admin to register new users, modify information of existing users, delete users from the system and
 * view the complete list of users
 */
@Controller
@RequestMapping("/backoffice/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/register")
    @ResponseBody
    public UserResponseDTO registerPost(@RequestBody @Valid UserDTO userDTO) {
        User registeredUser = adminService.registerUser(userDTO);
        return new UserResponseDTO(registeredUser);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        User updatedUser = adminService.updateUser(id, userDTO);
        return new UserResponseDTO(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<User> listUsers(Pageable pageable) {
        return adminService.listUsers(pageable);
    }
}