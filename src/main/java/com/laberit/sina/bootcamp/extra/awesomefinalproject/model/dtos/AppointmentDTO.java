package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private LocalDateTime date;
    private AppointmentStatus status;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.patientId = appointment.getPatient().getId();
        this.date = appointment.getDate();
        this.status = appointment.getStatus();
    }
}