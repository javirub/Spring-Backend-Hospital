package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Disease;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.DiagnosisRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.DiagnosisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkDoctorOfPatient;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final UnauthorizedAccessRepository unauthorizedAccessRepository;
    private final UserRepository userRepository;

    public DiagnosisServiceImpl(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository,
                                UnauthorizedAccessRepository unauthorizedAccessRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.unauthorizedAccessRepository = unauthorizedAccessRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Diagnosis createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO, String doctorsUsername) {
        checkPermissions("CREATE_DIAGNOSIS");

        Patient patient = patientRepository.findById(createDiagnosisDTO.getPatientId()).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }

        User doctor = userRepository.findByUsername(doctorsUsername).orElse(null);
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found");
        }

        checkDoctorOfPatient(patient, doctor, "Create Diagnosis", unauthorizedAccessRepository);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setPatient(patient);
        diagnosis.setDisease(Disease.valueOf(createDiagnosisDTO.getDisease()));
        diagnosis.setStatus(DiagnosisStatus.PENDING);

        diagnosisRepository.save(diagnosis);

        return diagnosis;
    }

    @Override
    @Transactional
    public Page<Diagnosis> listDiagnosis(Long patientId, String doctorsUsername, Pageable pageable) {
        checkPermissions("WATCH_DIAGNOSIS");

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new IllegalArgumentException("Patient not found"));

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found"));

        checkDoctorOfPatient(patient, doctor, "List Diagnosis", unauthorizedAccessRepository);

        return diagnosisRepository.findAllByPatient(patient, pageable);
    }

    @Override
    @Transactional
    public Diagnosis updateDiagnosisStatus(Long diagnosisId,
                                           DiagnosisStatus diagnosisStatus, String doctorsUsername) {
        checkPermissions("UPDATE_DIAGNOSIS");

        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow(() ->
                new IllegalArgumentException("Diagnosis not found"));

        Patient patient = diagnosis.getPatient();
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found"));

        checkDoctorOfPatient(patient, doctor, "Update Diagnosis", unauthorizedAccessRepository);

        diagnosis.setStatus(diagnosisStatus);
        diagnosisRepository.save(diagnosis);
        return diagnosis;
    }
}
