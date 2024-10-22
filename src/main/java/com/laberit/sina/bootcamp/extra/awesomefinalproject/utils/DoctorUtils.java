package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.UnauthorizedAccess;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;

import java.time.LocalDateTime;

public class DoctorUtils {
    /**
     * Check if the doctor is doctor of this patient
     *
     * @param patient  - patient
     * @param username doctor's username
     * @param query    - the query that the doctor is executing
     */
    public static void checkDoctorOfPatient(Patient patient, String username, String query,
                                            UnauthorizedAccessRepository unauthorizedAccessRepository) {
        boolean isDoctorOfPatient = patient.getDoctors().stream()
                .anyMatch(doctor -> doctor.getUsername().equals(username));

        if (!isDoctorOfPatient) {
            UnauthorizedAccess unauthorizedAccess = new UnauthorizedAccess();
            unauthorizedAccess.setPatientId(patient.getId());
            unauthorizedAccess.setDoctorUsername(username);
            unauthorizedAccess.setTimestamp(LocalDateTime.now());
            unauthorizedAccess.setQuery(query);

            unauthorizedAccessRepository.save(unauthorizedAccess);
        }
    }
}