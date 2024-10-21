package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DoctorUtils {
    /**
     * Check if the doctor is doctor of this patient
     * @param patient - patient
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


    /**
     * Check if the doctor has permission to access the patient
     * @param patientRepository - patient repository
     * @param patient - patient
     * @param username - doctor's username
     * @param permissions - permissions to check (e.g. "PERMISSION1", "PERMISSION2", ...)
     * @return null if doctor has permission, otherwise return bad request or forbidden response entity
     */
    public static ResponseEntity<?> checkPatientDoctorPermission(PatientRepository patientRepository, Patient patient, String username, String... permissions) {
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        ResponseEntity<?> isDoctorOfPatient = checkDoctorOfPatient(patient, username);
        if (isDoctorOfPatient != null) {
            return isDoctorOfPatient;
        }

        return PermissionUtils.checkPermissions(permissions);
    }
}