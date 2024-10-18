package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String surnames;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.username = userDTO.getUsername();
        this.surnames = userDTO.getSurnames();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }
}