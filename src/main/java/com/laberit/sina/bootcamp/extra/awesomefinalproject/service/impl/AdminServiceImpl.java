package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.RoleRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        ResponseEntity<?> hasPermissions = checkPermissions("REGISTER_USER");
        if (hasPermissions != null) {
            return hasPermissions;
        }

        Role role = roleRepository.findByName(RoleName.valueOf(userDTO.getRole())).orElse(null);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }

        User newUser = new User(userDTO, role);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        ResponseEntity<?> hasPermissions = checkPermissions("UPDATE_USER");
        if (hasPermissions != null) {
            return hasPermissions;
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (!userDTO.getUsername().equals("admin")) {
            user.setUsername(userDTO.getUsername());
        }
        user.setName(userDTO.getName());
        user.setSurnames(userDTO.getSurnames());
        roleRepository.findByName(RoleName.valueOf(userDTO.getRole())).ifPresent(user::setRole);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(Long id) {
        if (id == 1) {
            return ResponseEntity.badRequest().body("Admin account cannot be deleted");
        }
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        ResponseEntity<?> hasPermissions = checkPermissions("DELETE_USER");
        if (hasPermissions != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permissions to delete users");
        }

        userRepository.delete(user);
        return ResponseEntity.ok("User: " + id + " deleted");
    }

    @Override
    public ResponseEntity<?> listUsers(Pageable pageable) {
        ResponseEntity<?> hasPermissions = checkPermissions("WATCH_USERS");
        if (hasPermissions != null) {
            return hasPermissions;
        }
        Page<User> usersPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(usersPage);
    }
}