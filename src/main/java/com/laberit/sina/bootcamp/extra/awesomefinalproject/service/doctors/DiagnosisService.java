package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DiagnosisService {
    ResponseEntity<?> createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO, String doctorsUsername);

    ResponseEntity<?> listDiagnosis(Long patientId, String doctorsUsername, Pageable pageable);

    ResponseEntity<?> updateDiagnosis(Long diagnosisId, DiagnosisStatus diagnosisStatus, String doctorsUsername);
}
