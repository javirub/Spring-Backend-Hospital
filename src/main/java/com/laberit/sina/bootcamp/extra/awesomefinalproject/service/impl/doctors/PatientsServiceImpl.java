package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.PatientsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkDoctorOfPatient;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class PatientsServiceImpl implements PatientsService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final UnauthorizedAccessRepository unauthorizedAccessRepository;

    public PatientsServiceImpl(PatientRepository patientRepository, UserRepository userRepository,
                               UnauthorizedAccessRepository unauthorizedAccessRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.unauthorizedAccessRepository = unauthorizedAccessRepository;
    }

    @Override
    @Transactional
    public Page<Patient> listMyPatients(String doctorsUsername, Pageable pageable) {
        checkPermissions("WATCH_PATIENT_INFO");

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found"));

        return patientRepository.findByDoctorsContaining(doctor, pageable);
    }

    @Override
    @Transactional
    public Patient getPatientDetails(Long patientId, String doctorsUsername) {
        checkPermissions("WATCH_PATIENT_INFO");

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new IllegalArgumentException("Patient not found"));

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found"));

        checkDoctorOfPatient(patient, doctor, "Watch Patient Info", unauthorizedAccessRepository);

        return patient;
    }
}
