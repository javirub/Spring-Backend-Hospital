package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PatientDTO {
    private Long id;
    private String name;
    private String surnames;
    private LocalDate birthDate;
    private Gender gender;
    private List<DiagnosisResponseDTO> diagnosis;
    private List<AppointmentDTO> appointments;

    public PatientDTO(Patient patient){
        this.id = patient.getId();
        this.name = patient.getName();
        this.surnames = patient.getSurnames();
        this.birthDate = patient.getBirthDate();
        this.gender = patient.getGender();
        this.diagnosis = patient.getDiagnosis().stream().map(DiagnosisResponseDTO::new).collect(Collectors.toList());
        this.appointments = patient.getAppointments().stream().map(AppointmentDTO::new).collect(Collectors.toList());
    }
}
