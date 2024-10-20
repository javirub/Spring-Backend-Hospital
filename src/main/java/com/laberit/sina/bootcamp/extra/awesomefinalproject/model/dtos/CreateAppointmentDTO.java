package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentDTO {
    @NotNull(message = "Patient ID is mandatory")
    private Long patientId;

    @NotNull(message = "Date and time is mandatory")
    private LocalDateTime date;
}