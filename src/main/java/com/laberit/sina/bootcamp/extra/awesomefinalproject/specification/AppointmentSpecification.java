package com.laberit.sina.bootcamp.extra.awesomefinalproject.specification;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AppointmentSpecification {
    public static Specification<Appointment> hasPatient(Patient patient) {
        return (root, query, criteriaBuilder) ->
                patient == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("patient"), patient);
    }

    public static Specification<Appointment> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status").as(String.class), status);
    }

    public static Specification<Appointment> beforeDate(LocalDateTime beforeDate) {
        return (root, query, criteriaBuilder) ->
                beforeDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("date"), beforeDate);
    }

    public static Specification<Appointment> afterDate(LocalDateTime afterDate) {
        return (root, query, criteriaBuilder) ->
                afterDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("date"), afterDate);
    }

    public static Specification<Appointment> hasDoctor(User doctor) {
        return (root, query, criteriaBuilder) ->
                doctor == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("doctor"), doctor);
    }
}