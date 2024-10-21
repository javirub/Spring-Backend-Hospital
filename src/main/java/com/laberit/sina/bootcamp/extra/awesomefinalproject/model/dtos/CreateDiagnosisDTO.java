package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.validation.ValidDisease;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDiagnosisDTO {
    @ValidDisease
    private String disease;

    @NotNull(message = "Patient ID is required")
    private Long patientId;
}