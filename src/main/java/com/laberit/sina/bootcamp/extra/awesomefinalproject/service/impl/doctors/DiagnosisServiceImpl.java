package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.DiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Disease;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.DiagnosisRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.DiagnosisService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkDoctorOfPatient;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final UnauthorizedAccessRepository unauthorizedAccessRepository;

    public DiagnosisServiceImpl(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository,
                                UnauthorizedAccessRepository unauthorizedAccessRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.unauthorizedAccessRepository = unauthorizedAccessRepository;
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

        checkDoctorOfPatient(patient, doctorsUsername, "Create Diagnosis", unauthorizedAccessRepository);


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
        ResponseEntity<?> hasPermissions = checkPermissions("WATCH_DIAGNOSIS");
        if (hasPermissions != null) {
            return hasPermissions;
        }

        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        checkDoctorOfPatient(patient, doctorsUsername, "List Diagnosis", unauthorizedAccessRepository);

        return ResponseEntity.ok(diagnosisRepository.findAllByPatient(patient, pageable).map(DiagnosisDTO::new));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateDiagnosisStatus(Long diagnosisId,
                                                   DiagnosisStatus diagnosisStatus, String doctorsUsername) {
        ResponseEntity<?> hasPermissions = checkPermissions("UPDATE_DIAGNOSIS");
        if (hasPermissions != null) {
            return hasPermissions;
        }

        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElse(null);
        if (diagnosis == null) {
            return ResponseEntity.badRequest().body("Diagnosis not found");
        }

        Patient patient = diagnosis.getPatient();

        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        checkDoctorOfPatient(patient, doctorsUsername, "Update Diagnosis", unauthorizedAccessRepository);

        diagnosis.setStatus(diagnosisStatus);
        diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok(new DiagnosisDTO(diagnosis));
    }
}
