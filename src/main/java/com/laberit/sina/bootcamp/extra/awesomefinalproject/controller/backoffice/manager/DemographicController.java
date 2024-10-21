package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.DemographicService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backoffice/manager/patients")
public class DemographicController {
    private final DemographicService demographicService;

    public DemographicController(DemographicService demographicService) {
        this.demographicService = demographicService;
    }

    @GetMapping("/gender")
    @ResponseBody
    public ResponseEntity<?> patientsByGender() {
        return demographicService.patientsByGender();
    }

    @GetMapping("/age")
    @ResponseBody
    public ResponseEntity<?> patientsByAge() {
        return demographicService.patientsByAge();
    }

    @GetMapping("/diagnosis")
    @ResponseBody
    public ResponseEntity<?> patientsWithDiagnosis() {
        return demographicService.patientsWithDiagnosis();
    }
}
