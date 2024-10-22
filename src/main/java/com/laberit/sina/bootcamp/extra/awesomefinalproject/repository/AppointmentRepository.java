package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByPatientId(Long patientId, Pageable pageable);

    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    List<Object[]> countAppointmentsByStatus();

    @Query(value = "SELECT EXTRACT(YEAR FROM AGE(p.birth_date)) AS age, COUNT(a) " +
            "FROM appointment a JOIN patient p ON a.patient_id = p.id " +
            "WHERE a.status = 'CANCELLED' " +
            "GROUP BY age", nativeQuery = true)
    List<Object[]> countCancelledAppointmentsByAge();

    @Query("SELECT d.id AS doctorId, d.name, d.surnames, COUNT(a) AS appointmentCount " +
            "FROM Appointment a JOIN a.doctor d " +
            "WHERE a.status = 'CANCELLED' " +
            "GROUP BY d.id " +
            "ORDER BY appointmentCount DESC")
    Page<Object[]> countCancelledAppointmentsByDoctor(Pageable pageable);
}