package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.AppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import org.springframework.http.ResponseEntity;

public class AppointmentUtils {

    public static ResponseEntity<?> saveAppointmentAndReturn(AppointmentRepository appointmentRepository, Appointment appointment) {
        appointmentRepository.save(appointment);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setPatientId(appointment.getPatient().getId());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setStatus(appointment.getStatus());

        return ResponseEntity.ok(appointmentDTO);
    }
}