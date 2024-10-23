package com.laberit.sina.bootcamp.extra.awesomefinalproject.specification;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import org.springframework.data.jpa.domain.Specification;

public class DiagnosisSpecification {
    public static Specification<Diagnosis> hasDisease(String disease) {
        return (root, query, criteriaBuilder) ->
                disease == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder
                        .lower(root.get("disease").as(String.class)), disease.toLowerCase());
    }

    public static Specification<Diagnosis> hasPatient(Patient patient) {
        return (root, query, criteriaBuilder) ->
                patient == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("patient"), patient);
    }

    public static Specification<Diagnosis> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder.lower(root
                        .get("status").as(String.class)), status.toLowerCase());
    }
}