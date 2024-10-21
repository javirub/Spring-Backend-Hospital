package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByPatientId(Long patientId, Pageable pageable);

    long countByStatusIs(AppointmentStatus status);

    @Query(value = "SELECT EXTRACT(YEAR FROM AGE(p.birth_date)) AS age, COUNT(a) " +
            "FROM appointment a JOIN patient p ON a.patient_id = p.id " +
            "WHERE a.status = 'CANCELLED' " +
            "GROUP BY age", nativeQuery = true)
    List<Object[]> countCancelledAppointmentsByAge();
}