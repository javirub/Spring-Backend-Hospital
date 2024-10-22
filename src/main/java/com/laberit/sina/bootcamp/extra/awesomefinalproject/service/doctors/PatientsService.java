package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientsService {
    Page<Patient> listMyPatients(String doctorsUsername, Pageable pageable);

    Patient getPatientDetails(Long patientId, String doctorsUsername);
}
