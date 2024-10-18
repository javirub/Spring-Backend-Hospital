package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surnames are mandatory")
    private String surnames;

    @NotBlank(message = "Role is mandatory")
    private RoleName role;
}