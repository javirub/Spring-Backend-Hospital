package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.NoContentException;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.RoleRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.AdminService;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username" + userDTO.getUsername() + "already exists");
        }

        checkPermissions("REGISTER_USER");

        Role role = roleRepository.findByName(RoleName.valueOf(userDTO.getRole()))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User newUser = new User(userDTO, role);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username" + userDTO.getUsername() + "already exists");
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        checkPermissions("UPDATE_USER");

        if (!userDTO.getUsername().equals("admin")) {
            user.setUsername(userDTO.getUsername());
        } else {
            throw new IllegalArgumentException("Admin username cannot be changed");
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setSurnames(userDTO.getSurnames());

        roleRepository.findByName(RoleName.valueOf(userDTO.getRole())).ifPresent(user::setRole);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteUser(Long id) {
        checkPermissions("DELETE_USER");
        if (id == 1) {
            throw new IllegalArgumentException("Admin user cannot be deleted");
        }
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.delete(user);
        return ResponseEntity.ok(Map.of("id", id, "status", "Deleted"));
    }

    @Override
    @Transactional
    public Page<User> listUsers(Pageable pageable, String name, String surnames, String role,
                                String username, List<String> permissions) {
        checkPermissions("WATCH_USERS");

        Specification<User> spec = Specification.where(UserSpecification.hasName(name))
                .and(UserSpecification.hasSurnames(surnames))
                .and(UserSpecification.hasRole(role))
                .and(UserSpecification.hasPermissions(permissions))
                .and(UserSpecification.hasUsername(username));

        Page<User> usersList = userRepository.findAll(spec, pageable);

        if (usersList.isEmpty()) {
            throw new NoContentException("No users found");
        }

        return usersList;
    }
}