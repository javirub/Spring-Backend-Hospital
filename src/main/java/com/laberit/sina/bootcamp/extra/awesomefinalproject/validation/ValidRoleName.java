package com.laberit.sina.bootcamp.extra.awesomefinalproject.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleName {
    String message() default "Invalid role name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}