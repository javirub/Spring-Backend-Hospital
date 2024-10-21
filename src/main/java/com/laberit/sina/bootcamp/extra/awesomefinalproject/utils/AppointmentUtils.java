package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.AppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import org.springframework.http.ResponseEntity;

public class AppointmentUtils {
    /**
     * Save appointment and return it as a response entity
     *
     * @param appointmentRepository
     * @param appointment
     * @return ResponseEntity<?>
     */
    public static ResponseEntity<?> saveAppointmentAndReturn(AppointmentRepository appointmentRepository, Appointment appointment) {
        appointmentRepository.save(appointment);

        AppointmentDTO appointmentDTO = new AppointmentDTO(appointment);

        return ResponseEntity.ok(appointmentDTO);
    }
}