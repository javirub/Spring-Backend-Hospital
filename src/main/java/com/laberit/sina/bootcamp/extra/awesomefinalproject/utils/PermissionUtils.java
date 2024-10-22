package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.ForbiddenAccessException;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.UnauthorizedAccessException;
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
    public static void checkPermissions(String... requiredPermissions) throws UnauthorizedAccessException, ForbiddenAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedAccessException("User is not authenticated");
        }
        boolean hasAllPermissions = Arrays.stream(requiredPermissions)
                .allMatch(requiredPermission -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(requiredPermission)));

        if (!hasAllPermissions) {
            throw new ForbiddenAccessException("User does not have the required permissions");
        }
    }
}