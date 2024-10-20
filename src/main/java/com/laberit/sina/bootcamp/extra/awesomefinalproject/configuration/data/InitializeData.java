package com.laberit.sina.bootcamp.extra.awesomefinalproject.configuration.data;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.RoleRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class InitializeData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public InitializeData(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeUsers();
        initializePatients();
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

    private void initializePatients() {
        if (patientRepository.count() == 0) {
            Patient patient = new Patient();
            patient.setName("Dora");
            patient.setSurnames("La Exploradora");
            patient.setBirthDate(LocalDate.of(1993, 8, 14));
            patient.setGender(Gender.FEMALE);
            patientRepository.save(patient);
        }
    }
}
