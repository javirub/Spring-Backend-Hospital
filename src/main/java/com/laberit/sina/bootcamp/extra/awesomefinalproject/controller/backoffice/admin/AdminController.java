package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.admin;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserResponseDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for ADMIN management.
 * Allows admin to register new users, modify information of existing users, delete users from the system and
 * view the complete list of users
 */
@RestController
@RequestMapping("/backoffice/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/register")
    public UserResponseDTO registerPost(@RequestBody @Valid UserDTO userDTO) {
        User registeredUser = adminService.registerUser(userDTO);
        return new UserResponseDTO(registeredUser);
    }

    @PutMapping("/update/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        User updatedUser = adminService.updateUser(id, userDTO);
        return new UserResponseDTO(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/list")
    public List<User> listUsers(Pageable pageable,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String surnames,
                                @RequestParam(required = false) String role,
                                @RequestParam(required = false) String username,
                                @RequestParam(required = false) List<String> permissions) {
        return adminService.listUsers(pageable, name, surnames, role, username, permissions).getContent();
    }
}