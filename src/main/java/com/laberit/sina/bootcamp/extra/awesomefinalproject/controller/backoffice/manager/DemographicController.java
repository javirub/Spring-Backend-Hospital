package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.DemographicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/backoffice/manager/patients")
public class DemographicController {
    private final DemographicService demographicService;

    public DemographicController(DemographicService demographicService) {
        this.demographicService = demographicService;
    }

    @GetMapping("/gender")
    public Map<Gender, Long> patientsByGender() {
        return demographicService.patientsByGender();
    }

    @GetMapping("/age")
    public Map<String, Double> patientsMeanAge() {
        return demographicService.patientsMeanAge();
    }

    @GetMapping("/diagnosis")
    public Map<String, Long> patientsWithDiagnosis() {
        return demographicService.patientsWithDiagnosis();
    }
}
