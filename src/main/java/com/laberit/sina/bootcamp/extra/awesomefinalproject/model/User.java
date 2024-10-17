package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Permissions;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String surnames;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(targetClass = Permissions.class)
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.username = userDTO.getUsername();
        this.surnames = userDTO.getSurnames();
        this.password = userDTO.getPassword();
        this.role = Role.valueOf(userDTO.getRole());
        this.permissions = assignPermissionsBasedOnRole(role);
    }

    private List<Permissions> assignPermissionsBasedOnRole(Role role) {
        List<Permissions> permissions = new ArrayList<>();
        switch (role) {
            case ADMIN:
                permissions.addAll(Arrays.asList(Permissions.values()));
                break;
            case DOCTOR:
                permissions.add(Permissions.READ_DIAGNOSIS);
                permissions.add(Permissions.WRITE_DIAGNOSIS);
                permissions.add(Permissions.READ_APPOINTMENT);
                permissions.add(Permissions.WRITE_APPOINTMENT);
                permissions.add(Permissions.READ_PATIENT);
                permissions.add(Permissions.WRITE_PATIENT);
                break;
            case MANAGER:
                permissions.add(Permissions.READ_USER);
                permissions.add(Permissions.WRITE_USER);
                permissions.add(Permissions.READ_PATIENT);
                permissions.add(Permissions.WRITE_PATIENT);
                permissions.add(Permissions.CREATE_DOCTOR);
                break;
            default:
                break;
        }
        return permissions;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }
}