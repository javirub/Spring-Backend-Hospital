package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    Appointment createAppointment(CreateAppointmentDTO createAppointmentDTO, String doctorsUsername);

    Appointment confirmAppointment(Long appointmentId);

    Page<Appointment> listPatientAppointments(Long patientId, String status, String beforeDate, String afterDate,
                                              Long doctorId, String doctorsUsername, Pageable pageable);
}