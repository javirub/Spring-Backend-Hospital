package com.laberit.sina.bootcamp.extra.awesomefinalproject.validation;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleNameValidator implements ConstraintValidator<ValidRoleName, String> {

    private Set<String> validRoles;

    @Override
    public void initialize(ValidRoleName constraintAnnotation) {
        validRoles = EnumSet.allOf(RoleName.class).stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext context) {
        return roleName != null && validRoles.contains(roleName);
    }
}