package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PatientsService {
    ResponseEntity<?> listMyPatients(String doctorsUsername, Pageable pageable);

    ResponseEntity<?> getPatientDetails(Long patientId, String doctorsUsername);
}
