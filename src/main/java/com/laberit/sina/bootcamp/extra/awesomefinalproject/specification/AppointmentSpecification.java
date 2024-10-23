package com.laberit.sina.bootcamp.extra.awesomefinalproject.specification;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentSpecification {
    public static Specification<Appointment> hasPatient(Patient patient) {
        return (root, query, criteriaBuilder) ->
                patient == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("patient"), patient);
    }

    public static Specification<Appointment> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status").as(String.class), status);
    }

    public static Specification<Appointment> beforeDate(String beforeDate) {
        return (root, query, criteriaBuilder) ->
                beforeDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("date").as(String.class), beforeDate);
    }

    public static Specification<Appointment> afterDate(String afterDate) {
        return (root, query, criteriaBuilder) ->
                afterDate == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(String.class), afterDate);
    }

    public static Specification<Appointment> hasDoctor(User doctor) {
        return (root, query, criteriaBuilder) ->
                doctor == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("doctor"), doctor);
    }
}