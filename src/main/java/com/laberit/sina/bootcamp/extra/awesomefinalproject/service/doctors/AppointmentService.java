package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AppointmentService {
    ResponseEntity<?> createAppointment(CreateAppointmentDTO createAppointmentDTO);

    ResponseEntity<?> confirmAppointment(Long appointmentId);

    ResponseEntity<?> listPatientAppointments(Long patientId, String doctorsUsername, Pageable pageable);
}