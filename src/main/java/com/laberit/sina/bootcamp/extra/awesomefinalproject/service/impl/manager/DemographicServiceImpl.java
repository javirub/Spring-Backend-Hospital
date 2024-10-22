package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.DemographicService;
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
    public Map<String, Integer> patientsByGender() {
        checkPermissions("WATCH_PATIENT_STATISTICS");

        int maleCount = patientRepository.countPatientByGender(Gender.MALE);
        int femaleCount = patientRepository.countPatientByGender(Gender.FEMALE);

        return Map.of("male", maleCount, "female", femaleCount);
    }

    @Override
    @Transactional
    public Map<String, Double> patientsMeanAge() {
        checkPermissions("WATCH_PATIENT_STATISTICS");

        double meanAge = patientRepository.getMeanAge();

        return Map.of("meanAge", meanAge);
    }

    @Override
    @Transactional
    public Map<String, Long> patientsWithDiagnosis() {
        checkPermissions("WATCH_PATIENT_STATISTICS");

        long count = patientRepository.countByDiagnosisNotEmpty();

        return Map.of("count", count);
    }
}
