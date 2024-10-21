package com.laberit.sina.bootcamp.extra.awesomefinalproject.validation;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Disease;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DiseaseValidator implements ConstraintValidator<ValidDisease, String> {

    private Set<String> validDiseases;

    @Override
    public void initialize(ValidDisease constraintAnnotation) {
        validDiseases = EnumSet.allOf(Disease.class).stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String diseaseName, ConstraintValidatorContext context) {
        return diseaseName != null && validDiseases.contains(diseaseName);
    }
}