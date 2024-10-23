package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.GenderCount;

import java.util.List;
import java.util.Map;

public interface DemographicService {
    List<GenderCount> patientsByGender();

    Map<String, Double> patientsMeanAge();

    Map<String, Long> patientsWithDiagnosis();
}
