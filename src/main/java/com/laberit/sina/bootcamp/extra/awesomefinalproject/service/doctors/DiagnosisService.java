package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiagnosisService {
    Diagnosis createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO, String doctorsUsername);

    Page<Diagnosis> listDiagnosis(Long patientId, String doctorsUsername, Pageable pageable);

    Diagnosis updateDiagnosisStatus(Long diagnosisId, DiagnosisStatus diagnosisStatus, String doctorsUsername);
}
