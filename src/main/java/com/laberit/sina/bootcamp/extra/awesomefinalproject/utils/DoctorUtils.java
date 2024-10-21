package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DoctorUtils {
    /**
     * Check if the doctor is doctor of this patient
     * @param patient
     * @param username doctor's username
     * @return null if doctor is doctor of this patient, otherwise return forbidden response entity
     */
    public static ResponseEntity<?> checkDoctorOfPatient(Patient patient, String username) {
        boolean isDoctorOfPatient = patient.getDoctors().stream()
                .anyMatch(doctor -> doctor.getUsername().equals(username));

        if (!isDoctorOfPatient) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to view this patient's appointments");
        }
        return null;
    }
}