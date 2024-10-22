package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.user;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * Controller for user account management.
 * Allows own users change their password, and delete their account.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/change_password")
    public Map<String, String> changePassword(@RequestBody @Valid PasswordDTO passwordDTO, Principal principal) {
        String username = principal.getName();
        return userService.changePassword(passwordDTO, username);
    }

    @DeleteMapping("/delete")
    public Map<String, String> deleteUser(Principal principal) {
        String username = principal.getName();
        return userService.deleteUser(username);
    }
}