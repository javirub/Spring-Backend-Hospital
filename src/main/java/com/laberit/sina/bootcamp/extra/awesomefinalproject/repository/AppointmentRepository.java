package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.AppointmentStatusCount;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByAge;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByDoctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    Optional<Appointment> findFirstByDoctor(User doctor);

    @Query("SELECT a.status AS status, COUNT(a) AS count FROM Appointment a GROUP BY a.status")
    List<AppointmentStatusCount> countAppointmentsByStatus();

    @Query(value = "SELECT EXTRACT(YEAR FROM AGE(p.birth_date)) AS age, COUNT(a) AS count " +
            "FROM appointment a JOIN patient p ON a.patient_id = p.id " +
            "WHERE a.status = 'CANCELLED' " +
            "GROUP BY age " +
            "ORDER BY COUNT(a) DESC", nativeQuery = true)
    Page<CancelledAppointmentsByAge> countCancelledAppointmentsByAge(Pageable pageable);

    @Query("SELECT d.id AS doctorId, d.name as name, d.surnames as surnames, COUNT(a) AS appointmentCount " +
            "FROM Appointment a JOIN a.doctor d " +
            "WHERE a.status = 'CANCELLED' " +
            "GROUP BY d.id " +
            "ORDER BY appointmentCount DESC")
    Page<CancelledAppointmentsByDoctor> countCancelledAppointmentsByDoctor(Pageable pageable);
}