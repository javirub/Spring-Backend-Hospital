package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.DemographicService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class DemographicServiceImpl implements DemographicService {
    private final PatientRepository patientRepository;

    public DemographicServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> patientsByGender() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_PATIENT_STATISTICS");
        if (hasPermission != null) return hasPermission;
        int maleCount = patientRepository.countPatientByGender(Gender.MALE);
        int femaleCount = patientRepository.countPatientByGender(Gender.FEMALE);

        return ResponseEntity.ok().body(Map.of("male", maleCount, "female", femaleCount));
    }

    @Override
    @Transactional
    public ResponseEntity<?> patientsByAge() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_PATIENT_STATISTICS");
        if (hasPermission != null) return hasPermission;

        double meanAge = patientRepository.getMeanAge();

        return ResponseEntity.ok().body(meanAge);
    }

    @Override
    @Transactional
    public ResponseEntity<?> patientsWithDiagnosis() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_PATIENT_STATISTICS");
        if (hasPermission != null) return hasPermission;

        long count = patientRepository.countByDiagnosisNotEmpty();

        return ResponseEntity.ok().body(Map.of("count", count));
    }
}
