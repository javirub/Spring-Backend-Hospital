package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.DiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Disease;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.DiagnosisRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.DiagnosisService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkDoctorOfPatient;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkPatientDoctorPermission;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisServiceImpl(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO, String doctorsUsername) {
        ResponseEntity<?> hasPermissions = checkPermissions("CREATE_DIAGNOSIS");
        if (hasPermissions != null) {
            return hasPermissions;
        }

        Patient patient = patientRepository.findById(createDiagnosisDTO.getPatientId()).orElse(null);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        ResponseEntity<?> isDoctorOfPatient = checkDoctorOfPatient(patient, doctorsUsername);
        if (isDoctorOfPatient != null) {
            return isDoctorOfPatient;
        }

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setPatient(patient);
        diagnosis.setDisease(Disease.valueOf(createDiagnosisDTO.getDisease()));
        diagnosis.setStatus(DiagnosisStatus.PENDING);

        diagnosisRepository.save(diagnosis);

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO(diagnosis);
        return ResponseEntity.ok(diagnosisDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<?> listDiagnosis(Long patientId, String doctorsUsername, Pageable pageable) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        ResponseEntity<?> check = checkPatientDoctorPermission(patientRepository, patient,
                doctorsUsername, "WATCH_DIAGNOSIS");

        if (check != null) {
            return check;
        }

        return ResponseEntity.ok(diagnosisRepository.findAllByPatient(patient, pageable).map(DiagnosisDTO::new));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateDiagnosis(Long diagnosisId, DiagnosisStatus diagnosisStatus, String doctorsUsername) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElse(null);
        if (diagnosis == null) {
            return ResponseEntity.badRequest().body("Diagnosis not found");
        }

        ResponseEntity<?> check = checkPatientDoctorPermission(patientRepository, diagnosis.getPatient(),
                doctorsUsername, "UPDATE_DIAGNOSIS");

        if (check != null) {
            return check;
        }

        diagnosis.setStatus(diagnosisStatus);
        diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok(new DiagnosisDTO(diagnosis));
    }
}
