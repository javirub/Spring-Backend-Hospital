package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import java.util.Map;

public interface DemographicService {
    Map<String, Integer> patientsByGender();

    Map<String, Double> patientsMeanAge();

    Map<String, Long> patientsWithDiagnosis();
}
