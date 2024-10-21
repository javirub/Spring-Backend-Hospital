package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import org.springframework.http.ResponseEntity;

public interface DemographicService {
    ResponseEntity<?> patientsByGender();

    ResponseEntity<?> patientsByAge();

    ResponseEntity<?> patientsWithDiagnosis();
}
