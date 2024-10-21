package com.laberit.sina.bootcamp.extra.awesomefinalproject.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DiseaseValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDisease {
    String message() default "Invalid disease name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}