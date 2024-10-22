package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Role;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String name;
    private String surnames;
    private Role role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surnames = user.getSurnames();
        this.role = user.getRole();
    }
}