package com.laberit.sina.bootcamp.extra.awesomefinalproject.configuration.data;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.RoleRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class initialize implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeUsers();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("admin");
            userDTO.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(new User(userDTO, roleRepository.findByName(RoleName.ADMIN).orElseThrow()));

            userDTO = new UserDTO();
            userDTO.setUsername("doctor");
            userDTO.setPassword(passwordEncoder.encode("doctor"));
            userRepository.save(new User(userDTO, roleRepository.findByName(RoleName.DOCTOR).orElseThrow()));

            userDTO = new UserDTO();
            userDTO.setUsername("manager");
            userDTO.setPassword(passwordEncoder.encode("manager"));
            userRepository.save(new User(userDTO, roleRepository.findByName(RoleName.MANAGER).orElseThrow()));
        }
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                    new Role(RoleName.ADMIN),
                    new Role(RoleName.DOCTOR),
                    new Role(RoleName.MANAGER)
            ));
        }
    }
}
