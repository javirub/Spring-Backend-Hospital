package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> registerPost(@RequestBody @Valid UserDTO userDTO) {
        return adminService.registerUser(userDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return adminService.updateUser(id, userDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> listUsers(Pageable pageable) {
        return adminService.listUsers(pageable);
    }
}