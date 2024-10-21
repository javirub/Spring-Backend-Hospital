package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Disease;
import lombok.Data;

@Data
public class DiagnosisDTO {
    private Long id;
    private Disease disease;
    private DiagnosisStatus status;

    public DiagnosisDTO(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.disease = diagnosis.getDisease();
        this.status = diagnosis.getStatus();
    }
}