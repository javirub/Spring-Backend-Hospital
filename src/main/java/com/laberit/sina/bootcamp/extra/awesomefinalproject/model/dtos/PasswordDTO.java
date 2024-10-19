package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String confirmPassword;
}