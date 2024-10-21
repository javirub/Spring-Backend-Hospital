package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.user;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PasswordDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

/**
 * Controller for user account management.
 * Allows own users change their password, and delete their account.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/change_password")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordDTO passwordDTO, Principal principal) {
        String username = principal.getName();
        return userService.changePassword(passwordDTO, username);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteUser(Principal principal) {
        String username = principal.getName();
        if (Objects.equals(username, "admin")) {
            return ResponseEntity.badRequest().body("Admin account cannot be deleted");
        }
        return userService.deleteUser(username);
    }
}