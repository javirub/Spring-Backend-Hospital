package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;

import java.util.Map;

public interface DemographicService {
    Map<Gender, Long> patientsByGender();

    Map<String, Double> patientsMeanAge();

    Map<String, Long> patientsWithDiagnosis();
}
