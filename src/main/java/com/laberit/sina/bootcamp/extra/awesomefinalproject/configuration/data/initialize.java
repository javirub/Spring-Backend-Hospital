package com.laberit.sina.bootcamp.extra.awesomefinalproject.configuration.data;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class initialize implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        initializeUsers();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("admin");
            userDTO.setPassword(passwordEncoder.encode("admin"));
            userDTO.setRolename(String.valueOf(RoleName.ADMIN));
            User user = new User(userDTO);
            userRepository.save(user);
        }
    }
}
