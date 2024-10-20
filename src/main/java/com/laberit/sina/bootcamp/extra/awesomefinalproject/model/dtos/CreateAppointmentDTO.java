package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAppointmentDTO {
    @NotNull(message = "Patient ID is mandatory")
    private Long patientId;

    @NotNull(message = "Date is mandatory")
    private LocalDate date;
}