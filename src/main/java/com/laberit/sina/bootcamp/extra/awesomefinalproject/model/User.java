package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Permissions;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Surnames are mandatory")
    private String surnames;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(targetClass = Permissions.class)
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    public User() {
    }

    public User(String name, String username, String surnames, String password, Role role) {
        this.name = name;
        this.username = username;
        this.surnames = surnames;
        this.password = password;
        this.role = role;
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
                break;
            default:
                break;
        }
        return permissions;
    }
}