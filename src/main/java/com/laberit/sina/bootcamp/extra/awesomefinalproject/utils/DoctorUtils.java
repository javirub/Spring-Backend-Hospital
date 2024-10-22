package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.UnauthorizedAccess;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;

import java.time.LocalDateTime;

public class DoctorUtils {
    /**
     * Check if the doctor is doctor of this patient, If the doctor is not the doctor of the patient,
     * it saves the unauthorized access in Database.
     *
     * @param patient  - patient
     * @param doctor doctor's user
     * @param query    - the query that the doctor is executing
     * @param unauthorizedAccessRepository - repository for storing unauthorized access logs
     */
    public static void checkDoctorOfPatient(Patient patient, User doctor, String query,
                                            UnauthorizedAccessRepository unauthorizedAccessRepository) {
        boolean isDoctorOfPatient = patient.getDoctors().stream()
                .anyMatch(dctor -> dctor.equals(doctor));

        if (!isDoctorOfPatient) {
            UnauthorizedAccess unauthorizedAccess = new UnauthorizedAccess();
            unauthorizedAccess.setPatient(patient);
            unauthorizedAccess.setDoctor(doctor);
            unauthorizedAccess.setTimestamp(LocalDateTime.now());
            unauthorizedAccess.setQuery(query);

            unauthorizedAccessRepository.save(unauthorizedAccess);
        }
    }
}