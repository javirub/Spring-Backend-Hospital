package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

/**
 * Utility class for checking user permissions
 * Usage: checkPermissions("PERMISSION1", "PERMISSION2", ...)
 * Returns null if the user has all the required permissions, otherwise returns a ResponseEntity with the appropriate
 * status code and message.
 */
public class PermissionUtils {
    public static ResponseEntity<?> checkPermissions(String... requiredPermissions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        boolean hasAllPermissions = Arrays.stream(requiredPermissions)
                .allMatch(requiredPermission -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(requiredPermission)));

        if (!hasAllPermissions) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have the required permissions");
        }
        return null;
    }
}